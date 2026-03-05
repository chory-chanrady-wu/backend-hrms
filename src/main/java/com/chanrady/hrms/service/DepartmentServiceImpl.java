package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.DepartmentDTO;
import com.chanrady.hrms.entity.Department;
import com.chanrady.hrms.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setName(departmentDTO.getName());
        department.setDescription(departmentDTO.getDescription());
        Department savedDepartment = departmentRepository.save(department);
        return convertToDTO(savedDepartment);
    }

    @Override
    public DepartmentDTO updateDepartment(Integer id, DepartmentDTO departmentDTO) {
        Optional<Department> existingDepartment = departmentRepository.findById(id);
        if (existingDepartment.isPresent()) {
            Department department = existingDepartment.get();
            department.setName(departmentDTO.getName());
            department.setDescription(departmentDTO.getDescription());
            Department updatedDepartment = departmentRepository.save(department);
            return convertToDTO(updatedDepartment);
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
        dto.setCreatedAt(department.getCreatedAt());
        dto.setUpdatedAt(department.getUpdatedAt());
        return dto;
    }
}

