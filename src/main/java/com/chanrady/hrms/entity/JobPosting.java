package com.chanrady.hrms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "job_posting")
public class JobPosting {

    @Id
    @Column(name = "job_id", nullable = false, updatable = false)
    private UUID jobId;

    @Column(name = "job_title", length = 150, nullable = false)
    private String jobTitle;

    @Column(name = "department_id")
    private Integer departmentId;

    @Column(name = "hiring_manager_id")
    private Integer hiringManagerId;

    @Column(name = "job_description")
    private String jobDescription;

    @Column(name = "responsibilities")
    private String responsibilities;

    @Column(name = "requirements")
    private String requirements;

    @Column(name = "employment_type", length = 50)
    private String employmentType;

    @Column(name = "location", length = 150)
    private String location;

    @Column(name = "remote_option")
    private Boolean remoteOption = false;

    @Column(name = "salary")
    private String salary;

    @Column(name = "vacancies")
    private Integer vacancies = 1;

    @Column(name = "posting_date")
    private LocalDate postingDate;

    @Column(name = "closing_date")
    private LocalDate closingDate;

    @Column(name = "job_status", length = 50)
    private String jobStatus = "DRAFT";

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}

