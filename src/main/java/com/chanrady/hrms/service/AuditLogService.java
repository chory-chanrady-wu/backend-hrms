package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.AuditLogDTO;
import java.util.List;
import java.util.Optional;

public interface AuditLogService {
    AuditLogDTO createAuditLog(AuditLogDTO auditLogDTO);
    Optional<AuditLogDTO> getAuditLogById(Integer id);
    List<AuditLogDTO> getAllAuditLogs();
    List<AuditLogDTO> getAuditLogsByUser(Integer userId);
    List<AuditLogDTO> getAuditLogsByTableName(String tableName);
    List<AuditLogDTO> getAuditLogsByAction(String action);
}

