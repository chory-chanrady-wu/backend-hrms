package com.chanrady.hrms.repository;

import com.chanrady.hrms.entity.Attendance;
import com.chanrady.hrms.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    List<Attendance> findByEmployee(Employee employee);
    List<Attendance> findByDeletedAtIsNull();
}

