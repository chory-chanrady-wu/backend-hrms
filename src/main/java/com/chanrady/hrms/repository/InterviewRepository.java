package com.chanrady.hrms.repository;

import com.chanrady.hrms.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface InterviewRepository extends JpaRepository<Interview, UUID> {
}

