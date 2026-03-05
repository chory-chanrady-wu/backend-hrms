package com.chanrady.hrms.repository;

import com.chanrady.hrms.entity.Benefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BenefitRepository extends JpaRepository<Benefit, Integer> {
    Optional<Benefit> findByName(String name);
    List<Benefit> findByDeletedAtIsNull();
}

