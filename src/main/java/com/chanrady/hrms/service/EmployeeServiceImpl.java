package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.EmployeeDTO;
import com.chanrady.hrms.entity.Employee;
import com.chanrady.hrms.entity.User;
import com.chanrady.hrms.entity.Department;
import com.chanrady.hrms.entity.Position;
import com.chanrady.hrms.entity.Role;
import com.chanrady.hrms.repository.EmployeeRepository;
import com.chanrady.hrms.repository.UserRepository;
import com.chanrady.hrms.repository.DepartmentRepository;
import com.chanrady.hrms.repository.PositionRepository;
import com.chanrady.hrms.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private RoleRepository roleRepository;

    @Autowired
    private CloudinaryImageService cloudinaryImageService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
            if (employeeDTO.getUsername() == null || employeeDTO.getUsername().trim().isEmpty()) {
                throw new IllegalArgumentException("Username is required to create a new user for this employee");
            }
            if (employeeDTO.getEmail() == null || employeeDTO.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("Email is required to create a new user for this employee");
            }
            if (employeeDTO.getPassword() == null || employeeDTO.getPassword().trim().isEmpty()) {
                throw new IllegalArgumentException("Password is required to create a new user for this employee");
            }

            user = new User();
            user.setUsername(employeeDTO.getUsername());
            user.setFullName(employeeDTO.getFullName() != null ? employeeDTO.getFullName() : "");
            user.setEmail(employeeDTO.getEmail());
            user.setPhoneNumber(employeeDTO.getPhoneNumber());
            user.setPasswordHash(passwordEncoder.encode(employeeDTO.getPassword()));
            user.setStatus(true);

            // Default role for new users created through employee flow
            Role employeeRole = roleRepository.findByRoleName("EMPLOYEE")
                    .orElseThrow(() -> new IllegalArgumentException("Role EMPLOYEE not found"));
            user.setRole(employeeRole);

            user = userRepository.save(user);
        }

        Employee employee = new Employee();
        employee.setUser(user);
        employee.setEmploymentType(employeeDTO.getEmploymentType());
        employee.setSalary(employeeDTO.getSalary());
        employee.setHireDate(employeeDTO.getHireDate());
        employee.setDateOfBirth(employeeDTO.getDateOfBirth());
        employee.setNationality(employeeDTO.getNationality());
        employee.setAddress(employeeDTO.getAddress());
        employee.setStatus(employeeDTO.getStatus() != null ? employeeDTO.getStatus() : true);

        // Prefer uploaded image file; fallback to direct imageUrl string
        if (employeeDTO.getImage() != null && !employeeDTO.getImage().isEmpty()) {
            try {
                Map<String, Object> uploadResult = cloudinaryImageService.upload(employeeDTO.getImage());
                employee.setImageUrl((String) uploadResult.get("secure_url"));
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload employee image", e);
            }
        } else {
            employee.setImageUrl(employeeDTO.getImageUrl());
        }

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

            // Update user's full name if provided
            if (employeeDTO.getFullName() != null && !employeeDTO.getFullName().trim().isEmpty()) {
                User user = employee.getUser();
                if (user != null) {
                    user.setFullName(employeeDTO.getFullName());
                    userRepository.save(user);
                }
            }

            employee.setEmploymentType(employeeDTO.getEmploymentType());
            employee.setSalary(employeeDTO.getSalary());
            employee.setHireDate(employeeDTO.getHireDate());
            employee.setDateOfBirth(employeeDTO.getDateOfBirth());
            employee.setNationality(employeeDTO.getNationality());
            employee.setAddress(employeeDTO.getAddress());
            if (employeeDTO.getStatus() != null) {
                employee.setStatus(employeeDTO.getStatus());
            }
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
    public Optional<EmployeeDTO> getEmployeeByUserId(Integer userId) {
        return employeeRepository.findByUserId(userId).map(this::convertToDTO);
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
        // Convert string to Boolean for query
        Boolean statusBoolean = null;
        if ("active".equalsIgnoreCase(status) || "true".equalsIgnoreCase(status)) {
            statusBoolean = true;
        } else if ("inactive".equalsIgnoreCase(status) || "false".equalsIgnoreCase(status)) {
            statusBoolean = false;
        }

        if (statusBoolean == null) {
            return List.of();
        }

        // Query by Boolean status directly
        final Boolean finalStatus = statusBoolean;
        return employeeRepository.findAll()
                .stream()
                .filter(e -> e.getDeletedAt() == null && e.getStatus() != null && e.getStatus().equals(finalStatus))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean validateUserPassword(String usernameOrEmail, String password) {
        Optional<User> userOpt = userRepository.findByUsername(usernameOrEmail);
        if (userOpt.isEmpty()) {
            userOpt = userRepository.findByEmail(usernameOrEmail);
        }
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return passwordEncoder.matches(password, user.getPasswordHash());
        }
        return false;
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setEmploymentType(employee.getEmploymentType());
        dto.setSalary(employee.getSalary());
        dto.setHireDate(employee.getHireDate());
        dto.setDateOfBirth(employee.getDateOfBirth());
        dto.setNationality(employee.getNationality());
        dto.setAddress(employee.getAddress());
        dto.setStatus(employee.getStatus());
        dto.setImageUrl(employee.getImageUrl());
        if (employee.getUser() != null) {
            dto.setUserId(employee.getUser().getId());
            dto.setUsername(employee.getUser().getUsername());
            dto.setFullName(employee.getUser().getFullName());
            dto.setEmail(employee.getUser().getEmail());
            dto.setPhoneNumber(employee.getUser().getPhoneNumber());
            dto.setUserStatus(employee.getUser().getStatus() != null ? employee.getUser().getStatus().toString() : null);
        }
        if (employee.getDepartment() != null) {
            dto.setDepartmentId(employee.getDepartment().getId());
            dto.setDepartmentName(employee.getDepartment().getName());
            if (employee.getDepartment().getHeadOfDepartment() != null) {
                dto.setHeadOfDepartmentId(employee.getDepartment().getHeadOfDepartment().getId());
                dto.setHeadOfDepartmentName(employee.getDepartment().getHeadOfDepartment().getFullName());
            }
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
