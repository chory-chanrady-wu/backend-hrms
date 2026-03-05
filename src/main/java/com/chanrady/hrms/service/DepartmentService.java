package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.DepartmentDTO;
import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);
    DepartmentDTO updateDepartment(Integer id, DepartmentDTO departmentDTO);
    void deleteDepartment(Integer id);
    Optional<DepartmentDTO> getDepartmentById(Integer id);
    List<DepartmentDTO> getAllDepartments();
    Optional<DepartmentDTO> getDepartmentByName(String name);
}

