package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.EmployeeDTO;
import com.chanrady.hrms.entity.Employee;
import com.chanrady.hrms.entity.User;
import com.chanrady.hrms.entity.Department;
import com.chanrady.hrms.entity.Position;
import com.chanrady.hrms.repository.EmployeeRepository;
import com.chanrady.hrms.repository.UserRepository;
import com.chanrady.hrms.repository.DepartmentRepository;
import com.chanrady.hrms.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private CloudinaryImageService cloudinaryImageService;

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        User user;

        // If userId is provided, use existing user
        if (employeeDTO.getUserId() != null) {
            Optional<User> existingUser = userRepository.findById(employeeDTO.getUserId());
            if (existingUser.isEmpty()) {
                throw new IllegalArgumentException("User with ID " + employeeDTO.getUserId() + " not found");
            }
            user = existingUser.get();
        } else {
            // Create a new user if userId is not provided
            // Require username for new user creation
            if (employeeDTO.getUsername() == null || employeeDTO.getUsername().trim().isEmpty()) {
                throw new IllegalArgumentException("Username is required to create a new user for this employee");
            }
            if (employeeDTO.getEmail() == null || employeeDTO.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("Email is required to create a new user for this employee");
            }

            user = new User();
            user.setUsername(employeeDTO.getUsername());
            user.setFullName(employeeDTO.getFullName() != null ? employeeDTO.getFullName() : "");
            user.setEmail(employeeDTO.getEmail());
            user.setPasswordHash("default_password_hash"); // Default password hash - should be changed by user
            user = userRepository.save(user);
        }

        Employee employee = new Employee();
        employee.setUser(user);
        employee.setEmploymentType(employeeDTO.getEmploymentType());
        employee.setSalary(employeeDTO.getSalary());
        employee.setHireDate(employeeDTO.getHireDate());
        employee.setStatus(employeeDTO.getStatus());
        employee.setImageUrl(employeeDTO.getImageUrl());

        if (employeeDTO.getDepartmentId() != null) {
            Optional<Department> department = departmentRepository.findById(employeeDTO.getDepartmentId());
            department.ifPresent(employee::setDepartment);
        }

        if (employeeDTO.getPositionId() != null) {
            Optional<Position> position = positionRepository.findById(employeeDTO.getPositionId());
            position.ifPresent(employee::setPosition);
        }

        Employee savedEmployee = employeeRepository.save(employee);
        return convertToDTO(savedEmployee);
    }

    @Override
    public EmployeeDTO updateEmployee(Integer id, EmployeeDTO employeeDTO) {
        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if (existingEmployee.isPresent()) {
            Employee employee = existingEmployee.get();

            // If userId is provided, validate and update it
            if (employeeDTO.getUserId() != null) {
                Optional<User> user = userRepository.findById(employeeDTO.getUserId());
                if (user.isEmpty()) {
                    throw new IllegalArgumentException("User with ID " + employeeDTO.getUserId() + " not found");
                }
                employee.setUser(user.get());
            }

            employee.setEmploymentType(employeeDTO.getEmploymentType());
            employee.setSalary(employeeDTO.getSalary());
            employee.setHireDate(employeeDTO.getHireDate());
            employee.setStatus(employeeDTO.getStatus());
            employee.setImageUrl(employeeDTO.getImageUrl());

            if (employeeDTO.getDepartmentId() != null) {
                Optional<Department> department = departmentRepository.findById(employeeDTO.getDepartmentId());
                department.ifPresent(employee::setDepartment);
            }

            if (employeeDTO.getPositionId() != null) {
                Optional<Position> position = positionRepository.findById(employeeDTO.getPositionId());
                position.ifPresent(employee::setPosition);
            }

            Employee updatedEmployee = employeeRepository.save(employee);
            return convertToDTO(updatedEmployee);
        }
        return null;
    }

    @Override
    public EmployeeDTO updateEmployeeImage(Integer id, MultipartFile image) throws IOException {
        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if (existingEmployee.isPresent()) {
            Employee employee = existingEmployee.get();

            // Upload image to Cloudinary
            Map<String, Object> uploadResult = cloudinaryImageService.upload(image);
            String secureUrl = (String) uploadResult.get("secure_url");

            // Update employee with image URL
            employee.setImageUrl(secureUrl);
            Employee updatedEmployee = employeeRepository.save(employee);
            return convertToDTO(updatedEmployee);
        }
        return null;
    }

    @Override
    public void deleteEmployee(Integer id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        employee.ifPresent(e -> {
            e.setDeletedAt(LocalDateTime.now());
            employeeRepository.save(e);
        });
    }

    @Override
    public Optional<EmployeeDTO> getEmployeeById(Integer id) {
        return employeeRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findByDeletedAtIsNull()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> getEmployeesByDepartment(Integer departmentId) {
        Optional<Department> department = departmentRepository.findById(departmentId);
        if (department.isPresent()) {
            return employeeRepository.findByDepartment(department.get())
                    .stream()
                    .filter(e -> e.getDeletedAt() == null)
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    @Override
    public List<EmployeeDTO> getEmployeesByStatus(String status) {
        return employeeRepository.findByStatus(status)
                .stream()
                .filter(e -> e.getDeletedAt() == null)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setEmploymentType(employee.getEmploymentType());
        dto.setSalary(employee.getSalary());
        dto.setHireDate(employee.getHireDate());
        dto.setStatus(employee.getStatus());
        dto.setImageUrl(employee.getImageUrl());
        if (employee.getUser() != null) {
            dto.setUserId(employee.getUser().getId());
            dto.setUsername(employee.getUser().getUsername());
            dto.setFullName(employee.getUser().getFullName());
            dto.setEmail(employee.getUser().getEmail());
        }
        if (employee.getDepartment() != null) {
            dto.setDepartmentId(employee.getDepartment().getId());
            dto.setDepartmentName(employee.getDepartment().getName());
        }
        if (employee.getPosition() != null) {
            dto.setPositionId(employee.getPosition().getId());
            dto.setPositionName(employee.getPosition().getTitle());
        }
        dto.setCreatedAt(employee.getCreatedAt());
        dto.setUpdatedAt(employee.getUpdatedAt());
        return dto;
    }
}

