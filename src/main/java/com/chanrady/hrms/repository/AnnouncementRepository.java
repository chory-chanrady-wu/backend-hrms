package com.chanrady.hrms.repository;

import com.chanrady.hrms.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
    List<Announcement> findByDeletedAtIsNull();
    List<Announcement> findByStatusAndDeletedAtIsNull(Boolean status);
    List<Announcement> findByPriorityAndDeletedAtIsNull(String priority);
    List<Announcement> findByStatusAndExpiresAtAfterAndDeletedAtIsNull(Boolean status, LocalDateTime now);
}

