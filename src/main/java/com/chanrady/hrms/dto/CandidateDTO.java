package com.chanrady.hrms.dto;

import lombok.Data;
import java.util.UUID;
import java.time.LocalDate;

@Data
public class CandidateDTO {
    private UUID id;
    private String fullName;
    private String email;
    private String phone;
    private String resumeUrl;
    private LocalDate appliedDate;
    private UUID jobPostingId;
}

