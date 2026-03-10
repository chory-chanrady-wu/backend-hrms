package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.PayrollDTO;
import com.chanrady.hrms.entity.Payroll;
import com.chanrady.hrms.entity.Employee;
import com.chanrady.hrms.repository.PayrollRepository;
import com.chanrady.hrms.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PayrollServiceImpl implements PayrollService {

    @Autowired
    private PayrollRepository payrollRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public PayrollDTO createPayroll(PayrollDTO payrollDTO) {
        Payroll payroll = new Payroll();
        payroll.setBaseSalary(payrollDTO.getBaseSalary());
        payroll.setBonus(payrollDTO.getBonus() != null ? payrollDTO.getBonus() : BigDecimal.ZERO);
        payroll.setDeduction(payrollDTO.getDeduction() != null ? payrollDTO.getDeduction() : BigDecimal.ZERO);
        payroll.setPayDate(payrollDTO.getPayDate());
        payroll.setMonth(payrollDTO.getMonth());
        payroll.setYear(payrollDTO.getYear());

        if (payrollDTO.getEmployeeId() != null) {
            Optional<Employee> employee = employeeRepository.findById(payrollDTO.getEmployeeId());
            employee.ifPresent(payroll::setEmployee);
        }

        Payroll savedPayroll = payrollRepository.save(payroll);
        return convertToDTO(savedPayroll);
    }

    @Override
    public PayrollDTO updatePayroll(Integer id, PayrollDTO payrollDTO) {
        Optional<Payroll> existingPayroll = payrollRepository.findById(id);
        if (existingPayroll.isPresent()) {
            Payroll payroll = existingPayroll.get();
            payroll.setBaseSalary(payrollDTO.getBaseSalary());
            payroll.setBonus(payrollDTO.getBonus() != null ? payrollDTO.getBonus() : BigDecimal.ZERO);
            payroll.setDeduction(payrollDTO.getDeduction() != null ? payrollDTO.getDeduction() : BigDecimal.ZERO);
            payroll.setPayDate(payrollDTO.getPayDate());
            payroll.setMonth(payrollDTO.getMonth());
            payroll.setYear(payrollDTO.getYear());

            if (payrollDTO.getEmployeeId() != null) {
                Optional<Employee> employee = employeeRepository.findById(payrollDTO.getEmployeeId());
                employee.ifPresent(payroll::setEmployee);
            }

            Payroll updatedPayroll = payrollRepository.save(payroll);
            return convertToDTO(updatedPayroll);
        }
        return null;
    }

    @Override
    public void deletePayroll(Integer id) {
        Optional<Payroll> payroll = payrollRepository.findById(id);
        payroll.ifPresent(p -> {
            p.setDeletedAt(LocalDateTime.now());
            payrollRepository.save(p);
        });
    }

    @Override
    public Optional<PayrollDTO> getPayrollById(Integer id) {
        return payrollRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public List<PayrollDTO> getAllPayrolls() {
        return payrollRepository.findByDeletedAtIsNull()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PayrollDTO> getPayrollsByEmployee(Integer employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            return payrollRepository.findByEmployee(employee.get())
                    .stream()
                    .filter(p -> p.getDeletedAt() == null)
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    private PayrollDTO convertToDTO(Payroll payroll) {
        PayrollDTO dto = new PayrollDTO();
        dto.setId(payroll.getId());
        dto.setBaseSalary(payroll.getBaseSalary());
        dto.setBonus(payroll.getBonus());
        dto.setDeduction(payroll.getDeduction());

        // Fix: Initialize netSalary to BigDecimal.ZERO if baseSalary is null
        BigDecimal netSalary = payroll.getBaseSalary() != null ? payroll.getBaseSalary() : BigDecimal.ZERO;
        if (payroll.getBonus() != null) {
            netSalary = netSalary.add(payroll.getBonus());
        }
        if (payroll.getDeduction() != null) {
            netSalary = netSalary.subtract(payroll.getDeduction());
        }
        dto.setNetSalary(netSalary);

        dto.setPayDate(payroll.getPayDate());
        dto.setMonth(payroll.getMonth());
        dto.setYear(payroll.getYear());
        if (payroll.getEmployee() != null) {
            dto.setEmployeeId(payroll.getEmployee().getId());
            dto.setEmployeeName(payroll.getEmployee().getUser() != null ?
                    payroll.getEmployee().getUser().getFullName() : "N/A");
        }
        dto.setCreatedAt(payroll.getCreatedAt());
        dto.setUpdatedAt(payroll.getUpdatedAt());
        return dto;
    }
}
