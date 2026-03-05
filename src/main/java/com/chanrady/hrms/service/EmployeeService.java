package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.EmployeeDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO updateEmployee(Integer id, EmployeeDTO employeeDTO);
    EmployeeDTO updateEmployeeImage(Integer id, MultipartFile image) throws IOException;
    void deleteEmployee(Integer id);
    Optional<EmployeeDTO> getEmployeeById(Integer id);
    List<EmployeeDTO> getAllEmployees();
    List<EmployeeDTO> getEmployeesByDepartment(Integer departmentId);
    List<EmployeeDTO> getEmployeesByStatus(String status);
}

