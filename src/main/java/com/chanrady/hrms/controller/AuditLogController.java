package com.chanrady.hrms.controller;

import com.chanrady.hrms.dto.AuditLogDTO;
import com.chanrady.hrms.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/audit-logs")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    @PostMapping
    public ResponseEntity<AuditLogDTO> createAuditLog(@RequestBody AuditLogDTO auditLogDTO) {
        AuditLogDTO createdAuditLog = auditLogService.createAuditLog(auditLogDTO);
        return new ResponseEntity<>(createdAuditLog, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditLogDTO> getAuditLogById(@PathVariable Integer id) {
        Optional<AuditLogDTO> auditLog = auditLogService.getAuditLogById(id);
        return auditLog.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AuditLogDTO>> getAllAuditLogs() {
        List<AuditLogDTO> auditLogs = auditLogService.getAllAuditLogs();
        return ResponseEntity.ok(auditLogs);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AuditLogDTO>> getAuditLogsByUser(@PathVariable Integer userId) {
        List<AuditLogDTO> auditLogs = auditLogService.getAuditLogsByUser(userId);
        return ResponseEntity.ok(auditLogs);
    }

    @GetMapping("/table/{tableName}")
    public ResponseEntity<List<AuditLogDTO>> getAuditLogsByTableName(@PathVariable String tableName) {
        List<AuditLogDTO> auditLogs = auditLogService.getAuditLogsByTableName(tableName);
        return ResponseEntity.ok(auditLogs);
    }

    @GetMapping("/action/{action}")
    public ResponseEntity<List<AuditLogDTO>> getAuditLogsByAction(@PathVariable String action) {
        List<AuditLogDTO> auditLogs = auditLogService.getAuditLogsByAction(action);
        return ResponseEntity.ok(auditLogs);
    }
}

