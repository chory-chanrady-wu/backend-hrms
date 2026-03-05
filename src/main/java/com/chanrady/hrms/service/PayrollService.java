package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.PayrollDTO;
import java.util.List;
import java.util.Optional;

public interface PayrollService {
    PayrollDTO createPayroll(PayrollDTO payrollDTO);
    PayrollDTO updatePayroll(Integer id, PayrollDTO payrollDTO);
    void deletePayroll(Integer id);
    Optional<PayrollDTO> getPayrollById(Integer id);
    List<PayrollDTO> getAllPayrolls();
    List<PayrollDTO> getPayrollsByEmployee(Integer employeeId);
}

