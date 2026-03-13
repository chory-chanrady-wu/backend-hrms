package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.CandidateDTO;
import com.chanrady.hrms.entity.Candidate;
import com.chanrady.hrms.entity.JobPosting;
import com.chanrady.hrms.repository.CandidateRepository;
import com.chanrady.hrms.repository.JobPostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class CandidateServiceImpl implements CandidateService {
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private JobPostingRepository jobPostingRepository;

    private CandidateDTO toDTO(Candidate candidate) {
        CandidateDTO dto = new CandidateDTO();
        dto.setId(candidate.getId());
        dto.setFullName(candidate.getFullName());
        dto.setEmail(candidate.getEmail());
        dto.setPhone(candidate.getPhone());
        dto.setResumeUrl(candidate.getResumeUrl());
        dto.setAppliedDate(candidate.getAppliedDate());
        if (candidate.getJobPosting() != null) {
            dto.setJobPostingId(candidate.getJobPosting().getJobId());
        }
        return dto;
    }

    private Candidate toEntity(CandidateDTO dto) {
        Candidate candidate = new Candidate();
        candidate.setId(dto.getId());
        candidate.setFullName(dto.getFullName());
        candidate.setEmail(dto.getEmail());
        candidate.setPhone(dto.getPhone());
        candidate.setResumeUrl(dto.getResumeUrl());
        candidate.setAppliedDate(dto.getAppliedDate());
        if (dto.getJobPostingId() != null) {
            Optional<JobPosting> jobPosting = jobPostingRepository.findById(dto.getJobPostingId());
            jobPosting.ifPresent(candidate::setJobPosting);
        }
        return candidate;
    }

    @Override
    public CandidateDTO createCandidate(CandidateDTO candidateDTO) {
        Candidate candidate = toEntity(candidateDTO);
        return toDTO(candidateRepository.save(candidate));
    }

    @Override
    public CandidateDTO getCandidateById(UUID id) {
        return candidateRepository.findById(id).map(this::toDTO).orElse(null);
    }

    @Override
    public List<CandidateDTO> getAllCandidates() {
        return candidateRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public CandidateDTO updateCandidate(UUID id, CandidateDTO candidateDTO) {
        Optional<Candidate> optional = candidateRepository.findById(id);
        if (optional.isEmpty()) return null;
        Candidate candidate = optional.get();
        candidate.setFullName(candidateDTO.getFullName());
        candidate.setEmail(candidateDTO.getEmail());
        candidate.setPhone(candidateDTO.getPhone());
        candidate.setResumeUrl(candidateDTO.getResumeUrl());
        candidate.setAppliedDate(candidateDTO.getAppliedDate());
        if (candidateDTO.getJobPostingId() != null) {
            Optional<JobPosting> jobPosting = jobPostingRepository.findById(candidateDTO.getJobPostingId());
            jobPosting.ifPresent(candidate::setJobPosting);
        }
        return toDTO(candidateRepository.save(candidate));
    }

    @Override
    public void deleteCandidate(UUID id) {
        candidateRepository.deleteById(id);
    }
}
