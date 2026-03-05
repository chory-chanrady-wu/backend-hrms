package com.chanrady.hrms.controller;

import com.chanrady.hrms.dto.PayrollDTO;
import com.chanrady.hrms.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/payroll")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PayrollController {

    @Autowired
    private PayrollService payrollService;

    @PostMapping
    public ResponseEntity<PayrollDTO> createPayroll(@RequestBody PayrollDTO payrollDTO) {
        PayrollDTO createdPayroll = payrollService.createPayroll(payrollDTO);
        return new ResponseEntity<>(createdPayroll, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayrollDTO> getPayrollById(@PathVariable Integer id) {
        Optional<PayrollDTO> payroll = payrollService.getPayrollById(id);
        return payroll.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PayrollDTO>> getAllPayrolls() {
        List<PayrollDTO> payrolls = payrollService.getAllPayrolls();
        return ResponseEntity.ok(payrolls);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<PayrollDTO>> getPayrollsByEmployee(@PathVariable Integer employeeId) {
        List<PayrollDTO> payrolls = payrollService.getPayrollsByEmployee(employeeId);
        return ResponseEntity.ok(payrolls);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PayrollDTO> updatePayroll(@PathVariable Integer id, @RequestBody PayrollDTO payrollDTO) {
        PayrollDTO updatedPayroll = payrollService.updatePayroll(id, payrollDTO);
        if (updatedPayroll != null) {
            return ResponseEntity.ok(updatedPayroll);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayroll(@PathVariable Integer id) {
        payrollService.deletePayroll(id);
        return ResponseEntity.noContent().build();
    }
}

