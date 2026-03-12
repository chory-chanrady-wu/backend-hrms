package com.chanrady.hrms.controller;

import com.chanrady.hrms.entity.JobPosting;
import com.chanrady.hrms.dto.JobPostingDto;
import com.chanrady.hrms.service.JobPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/job-postings")
public class JobPostingController {

    @Autowired
    private JobPostingService jobPostingService;

    @GetMapping
    public List<JobPostingDto> getAllJobPostings() {
        return jobPostingService.getAllJobPostings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobPostingDto> getJobPostingById(@PathVariable UUID id) {
        Optional<JobPostingDto> jobPosting = jobPostingService.getJobPostingById(id);
        return jobPosting.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public JobPosting createJobPosting(@RequestBody JobPosting jobPosting) {
        return jobPostingService.createJobPosting(jobPosting);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobPosting> updateJobPosting(@PathVariable UUID id, @RequestBody JobPosting jobPosting) {
        JobPosting updated = jobPostingService.updateJobPosting(id, jobPosting);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobPosting(@PathVariable UUID id) {
        Optional<JobPostingDto> jobPosting = jobPostingService.getJobPostingById(id);
        if (!jobPosting.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        jobPostingService.deleteJobPosting(id);
        return ResponseEntity.noContent().build();
    }
}

