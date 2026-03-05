package com.chanrady.hrms.repository;

import com.chanrady.hrms.entity.Position;
import com.chanrady.hrms.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {
    List<Position> findByDepartment(Department department);
    List<Position> findByDeletedAtIsNull();
}

