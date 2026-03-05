package com.chanrady.hrms.repository;

import com.chanrady.hrms.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveType, Integer> {
    Optional<LeaveType> findByName(String name);
    List<LeaveType> findByDeletedAtIsNull();
}

