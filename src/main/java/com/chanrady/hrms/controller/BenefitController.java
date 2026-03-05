package com.chanrady.hrms.controller;

import com.chanrady.hrms.dto.BenefitDTO;
import com.chanrady.hrms.service.BenefitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/benefits")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BenefitController {

    @Autowired
    private BenefitService benefitService;

    @PostMapping
    public ResponseEntity<BenefitDTO> createBenefit(@RequestBody BenefitDTO benefitDTO) {
        BenefitDTO createdBenefit = benefitService.createBenefit(benefitDTO);
        return new ResponseEntity<>(createdBenefit, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BenefitDTO> getBenefitById(@PathVariable Integer id) {
        Optional<BenefitDTO> benefit = benefitService.getBenefitById(id);
        return benefit.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<BenefitDTO>> getAllBenefits() {
        List<BenefitDTO> benefits = benefitService.getAllBenefits();
        return ResponseEntity.ok(benefits);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<BenefitDTO> getBenefitByName(@PathVariable String name) {
        Optional<BenefitDTO> benefit = benefitService.getBenefitByName(name);
        return benefit.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BenefitDTO> updateBenefit(@PathVariable Integer id, @RequestBody BenefitDTO benefitDTO) {
        BenefitDTO updatedBenefit = benefitService.updateBenefit(id, benefitDTO);
        if (updatedBenefit != null) {
            return ResponseEntity.ok(updatedBenefit);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBenefit(@PathVariable Integer id) {
        benefitService.deleteBenefit(id);
        return ResponseEntity.noContent().build();
    }
}

