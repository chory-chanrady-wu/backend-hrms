package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.AnnouncementDTO;

import java.util.List;
import java.util.Optional;

public interface AnnouncementService {
    AnnouncementDTO createAnnouncement(AnnouncementDTO announcementDTO);
    AnnouncementDTO updateAnnouncement(Integer id, AnnouncementDTO announcementDTO);
    void deleteAnnouncement(Integer id);
    Optional<AnnouncementDTO> getAnnouncementById(Integer id);
    List<AnnouncementDTO> getAllAnnouncements();
    List<AnnouncementDTO> getActiveAnnouncements();
    List<AnnouncementDTO> getAnnouncementsByPriority(String priority);
    List<AnnouncementDTO> getActiveNonExpiredAnnouncements();
}

