package com.chanrady.hrms.repository;

import com.chanrady.hrms.entity.LeaveRequest;
import com.chanrady.hrms.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {
    List<LeaveRequest> findByEmployee(Employee employee);
    List<LeaveRequest> findByStatus(String status);
    List<LeaveRequest> findByDeletedAtIsNull();
}

