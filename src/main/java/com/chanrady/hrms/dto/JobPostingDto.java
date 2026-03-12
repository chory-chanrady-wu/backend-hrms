package com.chanrady.hrms.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class JobPostingDto {
    private UUID jobId;
    private String jobTitle;
    private Integer departmentId;
    private String departmentName;
    private Integer hiringManagerId;
    private String hiringManagerName;
    private String jobDescription;
    private String responsibilities;
    private String requirements;
    private String employmentType;
    private String location;
    private Boolean remoteOption;
    private String salary;
    private Integer vacancies;
    private LocalDate postingDate;
    private LocalDate closingDate;
    private String jobStatus;
    private String createdBy;
}

