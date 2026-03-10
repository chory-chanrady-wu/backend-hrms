package com.chanrady.hrms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayrollDTO {
    private Integer id;
    private Integer employeeId;
    private String employeeName;
    private BigDecimal baseSalary;
    private BigDecimal bonus;
    private BigDecimal deduction;
    private BigDecimal netSalary;
    private LocalDate payDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer month;
    private Integer year;
}
