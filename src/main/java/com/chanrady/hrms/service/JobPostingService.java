package com.chanrady.hrms.service;

import com.chanrady.hrms.entity.JobPosting;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.chanrady.hrms.dto.JobPostingDto;

public interface JobPostingService {
    List<JobPostingDto> getAllJobPostings();
    Optional<JobPostingDto> getJobPostingById(UUID id);
    JobPosting createJobPosting(JobPosting jobPosting);
    JobPosting updateJobPosting(UUID id, JobPosting jobPosting);
    void deleteJobPosting(UUID id);
}

