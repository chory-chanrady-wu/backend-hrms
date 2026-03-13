package com.chanrady.hrms.dto;

import lombok.Data;
import java.util.UUID;
import java.time.LocalDateTime;

@Data
public class InterviewDTO {
    private UUID id;
    private UUID candidateId;
    private LocalDateTime scheduledTime;
    private Integer interviewerId;
    private String location;
    private String status;
    private String feedback;
}

