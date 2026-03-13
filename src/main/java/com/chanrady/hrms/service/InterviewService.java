package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.InterviewDTO;
import java.util.List;
import java.util.UUID;

public interface InterviewService {
    InterviewDTO createInterview(InterviewDTO interviewDTO);
    InterviewDTO getInterviewById(UUID id);
    List<InterviewDTO> getAllInterviews();
    InterviewDTO updateInterview(UUID id, InterviewDTO interviewDTO);
    void deleteInterview(UUID id);
}

