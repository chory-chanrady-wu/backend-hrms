package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.AnnouncementDTO;
import com.chanrady.hrms.entity.Announcement;
import com.chanrady.hrms.entity.User;
import com.chanrady.hrms.repository.AnnouncementRepository;
import com.chanrady.hrms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public AnnouncementDTO createAnnouncement(AnnouncementDTO announcementDTO) {
        Announcement announcement = new Announcement();
        announcement.setTitle(announcementDTO.getTitle());
        announcement.setContent(announcementDTO.getContent());
        announcement.setPriority(announcementDTO.getPriority() != null ? announcementDTO.getPriority() : "MEDIUM");
        announcement.setStatus(announcementDTO.getStatus() != null ? announcementDTO.getStatus() : true);
        announcement.setPublishedAt(announcementDTO.getPublishedAt() != null ? announcementDTO.getPublishedAt() : LocalDateTime.now());
        announcement.setExpiresAt(announcementDTO.getExpiresAt());

        if (announcementDTO.getCreatedById() != null) {
            Optional<User> user = userRepository.findById(announcementDTO.getCreatedById());
            user.ifPresent(announcement::setCreatedBy);
        }

        Announcement savedAnnouncement = announcementRepository.save(announcement);
        return convertToDTO(savedAnnouncement);
    }

    @Override
    public AnnouncementDTO updateAnnouncement(Integer id, AnnouncementDTO announcementDTO) {
        Optional<Announcement> existingAnnouncement = announcementRepository.findById(id);
        if (existingAnnouncement.isPresent()) {
            Announcement announcement = existingAnnouncement.get();
            announcement.setTitle(announcementDTO.getTitle());
            announcement.setContent(announcementDTO.getContent());
            announcement.setPriority(announcementDTO.getPriority());
            if (announcementDTO.getStatus() != null) {
                announcement.setStatus(announcementDTO.getStatus());
            }
            announcement.setPublishedAt(announcementDTO.getPublishedAt());
            announcement.setExpiresAt(announcementDTO.getExpiresAt());

            if (announcementDTO.getCreatedById() != null) {
                Optional<User> user = userRepository.findById(announcementDTO.getCreatedById());
                user.ifPresent(announcement::setCreatedBy);
            }

            Announcement updatedAnnouncement = announcementRepository.save(announcement);
            return convertToDTO(updatedAnnouncement);
        }
        return null;
    }

    @Override
    public void deleteAnnouncement(Integer id) {
        Optional<Announcement> announcement = announcementRepository.findById(id);
        announcement.ifPresent(a -> {
            a.setDeletedAt(LocalDateTime.now());
            announcementRepository.save(a);
        });
    }

    @Override
    public Optional<AnnouncementDTO> getAnnouncementById(Integer id) {
        return announcementRepository.findById(id)
                .filter(a -> a.getDeletedAt() == null)
                .map(this::convertToDTO);
    }

    @Override
    public List<AnnouncementDTO> getAllAnnouncements() {
        return announcementRepository.findByDeletedAtIsNull()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnnouncementDTO> getActiveAnnouncements() {
        return announcementRepository.findByStatusAndDeletedAtIsNull(true)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnnouncementDTO> getAnnouncementsByPriority(String priority) {
        return announcementRepository.findByPriorityAndDeletedAtIsNull(priority)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnnouncementDTO> getActiveNonExpiredAnnouncements() {
        return announcementRepository.findByStatusAndExpiresAtAfterAndDeletedAtIsNull(true, LocalDateTime.now())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private AnnouncementDTO convertToDTO(Announcement announcement) {
        AnnouncementDTO dto = new AnnouncementDTO();
        dto.setId(announcement.getId());
        dto.setTitle(announcement.getTitle());
        dto.setContent(announcement.getContent());
        dto.setPriority(announcement.getPriority());
        dto.setStatus(announcement.getStatus());
        dto.setPublishedAt(announcement.getPublishedAt());
        dto.setExpiresAt(announcement.getExpiresAt());
        if (announcement.getCreatedBy() != null) {
            dto.setCreatedById(announcement.getCreatedBy().getId());
            dto.setCreatedByName(announcement.getCreatedBy().getFullName());
        }
        dto.setCreatedAt(announcement.getCreatedAt());
        dto.setUpdatedAt(announcement.getUpdatedAt());
        return dto;
    }
}

