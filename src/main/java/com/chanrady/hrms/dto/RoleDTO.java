package com.chanrady.hrms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private Integer id;
    private String roleName;
    private String permissions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

