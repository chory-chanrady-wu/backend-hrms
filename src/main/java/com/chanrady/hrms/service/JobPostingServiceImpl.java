package com.chanrady.hrms.service;

import com.chanrady.hrms.entity.JobPosting;
import com.chanrady.hrms.repository.JobPostingRepository;
import com.chanrady.hrms.dto.JobPostingDto;
import com.chanrady.hrms.repository.DepartmentRepository;
import com.chanrady.hrms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class JobPostingServiceImpl implements JobPostingService {

    @Autowired
    private JobPostingRepository jobPostingRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<JobPostingDto> getAllJobPostings() {
        List<JobPosting> jobs = jobPostingRepository.findAll();
        return jobs.stream().map(this::toDto).toList();
    }

    @Override
    public Optional<JobPostingDto> getJobPostingById(UUID id) {
        return jobPostingRepository.findById(id).map(this::toDto);
    }

    private JobPostingDto toDto(JobPosting jobPosting) {
        JobPostingDto dto = new JobPostingDto();
        dto.setJobId(jobPosting.getJobId());
        dto.setJobTitle(jobPosting.getJobTitle());
        dto.setDepartmentId(jobPosting.getDepartmentId());
        dto.setHiringManagerId(jobPosting.getHiringManagerId());
        dto.setJobDescription(jobPosting.getJobDescription());
        dto.setResponsibilities(jobPosting.getResponsibilities());
        dto.setRequirements(jobPosting.getRequirements());
        dto.setEmploymentType(jobPosting.getEmploymentType());
        dto.setLocation(jobPosting.getLocation());
        dto.setRemoteOption(jobPosting.getRemoteOption());
        dto.setSalary(jobPosting.getSalary());
        dto.setVacancies(jobPosting.getVacancies());
        dto.setPostingDate(jobPosting.getPostingDate());
        dto.setClosingDate(jobPosting.getClosingDate());
        dto.setJobStatus(jobPosting.getJobStatus());
        dto.setCreatedBy(jobPosting.getCreatedBy());
        // Fetch department name
        if (jobPosting.getDepartmentId() != null) {
            departmentRepository.findById(jobPosting.getDepartmentId())
                .ifPresent(dept -> dto.setDepartmentName(dept.getName()));
        }
        // Fetch hiring manager name
        if (jobPosting.getHiringManagerId() != null) {
            userRepository.findById(jobPosting.getHiringManagerId())
                .ifPresent(user -> dto.setHiringManagerName(user.getFullName()));
        }
        return dto;
    }

    @Override
    public JobPosting createJobPosting(JobPosting jobPosting) {
        if (jobPosting.getJobId() == null) {
            jobPosting.setJobId(UUID.randomUUID());
        }
        if (jobPosting.getCreatedAt() == null) {
            jobPosting.setCreatedAt(java.time.LocalDateTime.now());
        }
        return jobPostingRepository.save(jobPosting);
    }

    @Override
    public JobPosting updateJobPosting(UUID id, JobPosting jobPosting) {
        if (!jobPostingRepository.existsById(id)) {
            return null;
        }
        jobPosting.setJobId(id);
        return jobPostingRepository.save(jobPosting);
    }

    @Override
    public void deleteJobPosting(UUID id) {
        jobPostingRepository.deleteById(id);
        }
    }
// ...existing code...

