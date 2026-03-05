package com.chanrady.hrms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionDTO {
    private Integer id;

    @JsonProperty("positionName")
    private String title;

    private String description;
    private Integer departmentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

