package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.InterviewDTO;
import com.chanrady.hrms.entity.Interview;
import com.chanrady.hrms.entity.Candidate;
import com.chanrady.hrms.repository.InterviewRepository;
import com.chanrady.hrms.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class InterviewServiceImpl implements InterviewService {
    @Autowired
    private InterviewRepository interviewRepository;
    @Autowired
    private CandidateRepository candidateRepository;

    private InterviewDTO toDTO(Interview interview) {
        InterviewDTO dto = new InterviewDTO();
        dto.setId(interview.getId());
        if (interview.getCandidate() != null) {
            dto.setCandidateId(interview.getCandidate().getId());
        }
        dto.setScheduledTime(interview.getScheduledTime());
        dto.setInterviewerId(interview.getInterviewerId());
        dto.setLocation(interview.getLocation());
        dto.setStatus(interview.getStatus());
        dto.setFeedback(interview.getFeedback());
        return dto;
    }

    private Interview toEntity(InterviewDTO dto) {
        Interview interview = new Interview();
        interview.setId(dto.getId());
        if (dto.getCandidateId() != null) {
            Optional<Candidate> candidate = candidateRepository.findById(dto.getCandidateId());
            candidate.ifPresent(interview::setCandidate);
        }
        interview.setScheduledTime(dto.getScheduledTime());
        interview.setInterviewerId(dto.getInterviewerId());
        interview.setLocation(dto.getLocation());
        interview.setStatus(dto.getStatus());
        interview.setFeedback(dto.getFeedback());
        return interview;
    }

    @Override
    public InterviewDTO createInterview(InterviewDTO interviewDTO) {
        Interview interview = toEntity(interviewDTO);
        return toDTO(interviewRepository.save(interview));
    }

    @Override
    public InterviewDTO getInterviewById(UUID id) {
        return interviewRepository.findById(id).map(this::toDTO).orElse(null);
    }

    @Override
    public List<InterviewDTO> getAllInterviews() {
        return interviewRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public InterviewDTO updateInterview(UUID id, InterviewDTO interviewDTO) {
        Optional<Interview> optional = interviewRepository.findById(id);
        if (optional.isEmpty()) return null;
        Interview interview = optional.get();
        if (interviewDTO.getCandidateId() != null) {
            Optional<Candidate> candidate = candidateRepository.findById(interviewDTO.getCandidateId());
            candidate.ifPresent(interview::setCandidate);
        }
        interview.setScheduledTime(interviewDTO.getScheduledTime());
        interview.setInterviewerId(interviewDTO.getInterviewerId());
        interview.setLocation(interviewDTO.getLocation());
        interview.setStatus(interviewDTO.getStatus());
        interview.setFeedback(interviewDTO.getFeedback());
        return toDTO(interviewRepository.save(interview));
    }

    @Override
    public void deleteInterview(UUID id) {
        interviewRepository.deleteById(id);
    }
}

