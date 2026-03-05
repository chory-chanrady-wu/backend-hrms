package com.chanrady.hrms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogDTO {
    private Integer id;
    private Integer userId;
    private String username;
    private String action;
    private String tableName;
    private Integer recordId;
    private String oldData;
    private String newData;
    private String ipAddress;
    private LocalDateTime createdAt;
}

