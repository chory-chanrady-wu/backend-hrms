package com.chanrady.hrms.repository;

import com.chanrady.hrms.entity.EmployeeBenefit;
import com.chanrady.hrms.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeBenefitRepository extends JpaRepository<EmployeeBenefit, Integer> {
    List<EmployeeBenefit> findByEmployee(Employee employee);
    List<EmployeeBenefit> findByDeletedAtIsNull();
}

