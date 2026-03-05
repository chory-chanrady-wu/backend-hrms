package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.EmployeeBenefitDTO;
import java.util.List;
import java.util.Optional;

public interface EmployeeBenefitService {
    EmployeeBenefitDTO createEmployeeBenefit(EmployeeBenefitDTO employeeBenefitDTO);
    EmployeeBenefitDTO updateEmployeeBenefit(Integer id, EmployeeBenefitDTO employeeBenefitDTO);
    void deleteEmployeeBenefit(Integer id);
    Optional<EmployeeBenefitDTO> getEmployeeBenefitById(Integer id);
    List<EmployeeBenefitDTO> getAllEmployeeBenefits();
    List<EmployeeBenefitDTO> getEmployeeBenefitsByEmployee(Integer employeeId);
}

