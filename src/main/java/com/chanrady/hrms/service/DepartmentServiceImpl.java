package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.DepartmentDTO;
import com.chanrady.hrms.entity.Department;
import com.chanrady.hrms.entity.User;
import com.chanrady.hrms.repository.DepartmentRepository;
import com.chanrady.hrms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        String normalizedName = normalizeName(departmentDTO.getName());
        if (normalizedName == null || normalizedName.isEmpty()) {
            throw new IllegalArgumentException("Department name is required");
        }
        if (departmentRepository.existsByNameIgnoreCase(normalizedName)) {
            throw new IllegalArgumentException("Department name already exists: " + normalizedName);
        }

        Department department = new Department();
        department.setName(normalizedName);
        department.setDescription(departmentDTO.getDescription());

        if (departmentDTO.getHeadOfDepartmentId() != null) {
            Optional<User> headOfDepartment = userRepository.findById(departmentDTO.getHeadOfDepartmentId());
            if (headOfDepartment.isEmpty()) {
                throw new IllegalArgumentException("Head of department user not found: " + departmentDTO.getHeadOfDepartmentId());
            }
            department.setHeadOfDepartment(headOfDepartment.get());
        }

        try {
            Department savedDepartment = departmentRepository.save(department);
            return convertToDTO(savedDepartment);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("Department name already exists: " + normalizedName);
        }
    }

    @Override
    public DepartmentDTO updateDepartment(Integer id, DepartmentDTO departmentDTO) {
        Optional<Department> existingDepartment = departmentRepository.findById(id);
        if (existingDepartment.isPresent()) {
            Department department = existingDepartment.get();

            String normalizedName = normalizeName(departmentDTO.getName());
            if (normalizedName == null || normalizedName.isEmpty()) {
                throw new IllegalArgumentException("Department name is required");
            }
            if (departmentRepository.existsByNameIgnoreCaseAndIdNot(normalizedName, id)) {
                throw new IllegalArgumentException("Department name already exists: " + normalizedName);
            }

            department.setName(normalizedName);
            department.setDescription(departmentDTO.getDescription());

            if (departmentDTO.getHeadOfDepartmentId() != null) {
                Optional<User> headOfDepartment = userRepository.findById(departmentDTO.getHeadOfDepartmentId());
                if (headOfDepartment.isEmpty()) {
                    throw new IllegalArgumentException("Head of department user not found: " + departmentDTO.getHeadOfDepartmentId());
                }
                department.setHeadOfDepartment(headOfDepartment.get());
            } else {
                department.setHeadOfDepartment(null);
            }

            try {
                Department updatedDepartment = departmentRepository.save(department);
                return convertToDTO(updatedDepartment);
            } catch (DataIntegrityViolationException ex) {
                throw new IllegalArgumentException("Department name already exists: " + normalizedName);
            }
        }
        return null;
    }

    @Override
    public void deleteDepartment(Integer id) {
        Optional<Department> department = departmentRepository.findById(id);
        department.ifPresent(d -> {
            d.setDeletedAt(LocalDateTime.now());
            departmentRepository.save(d);
        });
    }

    @Override
    public Optional<DepartmentDTO> getDepartmentById(Integer id) {
        return departmentRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findByDeletedAtIsNull()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DepartmentDTO> getDepartmentByName(String name) {
        return departmentRepository.findByName(name).map(this::convertToDTO);
    }

    private DepartmentDTO convertToDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setDescription(department.getDescription());

        if (department.getHeadOfDepartment() != null) {
            dto.setHeadOfDepartmentId(department.getHeadOfDepartment().getId());
            dto.setHeadOfDepartmentName(department.getHeadOfDepartment().getFullName());
        }

        dto.setCreatedAt(department.getCreatedAt());
        dto.setUpdatedAt(department.getUpdatedAt());
        return dto;
    }

    private String normalizeName(String name) {
        return name == null ? null : name.trim();
    }
}
