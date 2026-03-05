package com.chanrady.hrms.controller;

import com.chanrady.hrms.dto.EmployeeBenefitDTO;
import com.chanrady.hrms.service.EmployeeBenefitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/employee-benefits")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EmployeeBenefitController {

    @Autowired
    private EmployeeBenefitService employeeBenefitService;

    @PostMapping
    public ResponseEntity<EmployeeBenefitDTO> createEmployeeBenefit(@RequestBody EmployeeBenefitDTO employeeBenefitDTO) {
        EmployeeBenefitDTO createdEmployeeBenefit = employeeBenefitService.createEmployeeBenefit(employeeBenefitDTO);
        return new ResponseEntity<>(createdEmployeeBenefit, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeBenefitDTO> getEmployeeBenefitById(@PathVariable Integer id) {
        Optional<EmployeeBenefitDTO> employeeBenefit = employeeBenefitService.getEmployeeBenefitById(id);
        return employeeBenefit.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EmployeeBenefitDTO>> getAllEmployeeBenefits() {
        List<EmployeeBenefitDTO> employeeBenefits = employeeBenefitService.getAllEmployeeBenefits();
        return ResponseEntity.ok(employeeBenefits);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<EmployeeBenefitDTO>> getEmployeeBenefitsByEmployee(@PathVariable Integer employeeId) {
        List<EmployeeBenefitDTO> employeeBenefits = employeeBenefitService.getEmployeeBenefitsByEmployee(employeeId);
        return ResponseEntity.ok(employeeBenefits);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeBenefitDTO> updateEmployeeBenefit(@PathVariable Integer id, @RequestBody EmployeeBenefitDTO employeeBenefitDTO) {
        EmployeeBenefitDTO updatedEmployeeBenefit = employeeBenefitService.updateEmployeeBenefit(id, employeeBenefitDTO);
        if (updatedEmployeeBenefit != null) {
            return ResponseEntity.ok(updatedEmployeeBenefit);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeBenefit(@PathVariable Integer id) {
        employeeBenefitService.deleteEmployeeBenefit(id);
        return ResponseEntity.noContent().build();
    }
}

