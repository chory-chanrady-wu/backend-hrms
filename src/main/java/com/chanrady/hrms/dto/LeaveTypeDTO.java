package com.chanrady.hrms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveTypeDTO {
    private Integer id;
    private String name;
    private Integer maxDays;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

