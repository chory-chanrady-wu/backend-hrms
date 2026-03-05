package com.chanrady.hrms.repository;

import com.chanrady.hrms.entity.Payroll;
import com.chanrady.hrms.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Integer> {
    List<Payroll> findByEmployee(Employee employee);
    List<Payroll> findByDeletedAtIsNull();
}

