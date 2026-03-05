package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.AuditLogDTO;
import com.chanrady.hrms.entity.AuditLog;
import com.chanrady.hrms.entity.User;
import com.chanrady.hrms.repository.AuditLogRepository;
import com.chanrady.hrms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public AuditLogDTO createAuditLog(AuditLogDTO auditLogDTO) {
        AuditLog auditLog = new AuditLog();
        auditLog.setAction(auditLogDTO.getAction());
        auditLog.setTableName(auditLogDTO.getTableName());
        auditLog.setRecordId(auditLogDTO.getRecordId());
        auditLog.setOldData(auditLogDTO.getOldData());
        auditLog.setNewData(auditLogDTO.getNewData());
        auditLog.setIpAddress(auditLogDTO.getIpAddress());

        if (auditLogDTO.getUserId() != null) {
            Optional<User> user = userRepository.findById(auditLogDTO.getUserId());
            user.ifPresent(auditLog::setUser);
        }

        AuditLog savedAuditLog = auditLogRepository.save(auditLog);
        return convertToDTO(savedAuditLog);
    }

    @Override
    public Optional<AuditLogDTO> getAuditLogById(Integer id) {
        return auditLogRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public List<AuditLogDTO> getAllAuditLogs() {
        return auditLogRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuditLogDTO> getAuditLogsByUser(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return auditLogRepository.findByUser(user.get())
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    @Override
    public List<AuditLogDTO> getAuditLogsByTableName(String tableName) {
        return auditLogRepository.findByTableName(tableName)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuditLogDTO> getAuditLogsByAction(String action) {
        return auditLogRepository.findByAction(action)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private AuditLogDTO convertToDTO(AuditLog auditLog) {
        AuditLogDTO dto = new AuditLogDTO();
        dto.setId(auditLog.getId());
        dto.setAction(auditLog.getAction());
        dto.setTableName(auditLog.getTableName());
        dto.setRecordId(auditLog.getRecordId());
        dto.setOldData(auditLog.getOldData());
        dto.setNewData(auditLog.getNewData());
        dto.setIpAddress(auditLog.getIpAddress());
        if (auditLog.getUser() != null) {
            dto.setUserId(auditLog.getUser().getId());
            dto.setUsername(auditLog.getUser().getUsername());
        }
        dto.setCreatedAt(auditLog.getCreatedAt());
        return dto;
    }
}

