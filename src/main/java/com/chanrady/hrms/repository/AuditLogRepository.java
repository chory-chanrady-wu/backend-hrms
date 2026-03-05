package com.chanrady.hrms.repository;

import com.chanrady.hrms.entity.AuditLog;
import com.chanrady.hrms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Integer> {
    List<AuditLog> findByUser(User user);
    List<AuditLog> findByTableName(String tableName);
    List<AuditLog> findByAction(String action);
}

