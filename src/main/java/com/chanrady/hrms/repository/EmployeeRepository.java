package com.chanrady.hrms.repository;

import com.chanrady.hrms.entity.Employee;
import com.chanrady.hrms.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByDepartment(Department department);
    List<Employee> findByStatus(String status);
    List<Employee> findByDeletedAtIsNull();
    Optional<Employee> findByUserId(Integer userId);
}

