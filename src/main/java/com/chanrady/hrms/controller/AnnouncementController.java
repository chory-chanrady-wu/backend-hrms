package com.chanrady.hrms.controller;

import com.chanrady.hrms.dto.AnnouncementDTO;
import com.chanrady.hrms.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @PostMapping
    public ResponseEntity<AnnouncementDTO> createAnnouncement(@RequestBody AnnouncementDTO announcementDTO) {
        AnnouncementDTO created = announcementService.createAnnouncement(announcementDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnnouncementDTO> updateAnnouncement(
            @PathVariable Integer id,
            @RequestBody AnnouncementDTO announcementDTO) {
        AnnouncementDTO updated = announcementService.updateAnnouncement(id, announcementDTO);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteAnnouncement(@PathVariable Integer id) {
        announcementService.deleteAnnouncement(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Announcement deleted successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementDTO> getAnnouncementById(@PathVariable Integer id) {
        Optional<AnnouncementDTO> announcement = announcementService.getAnnouncementById(id);
        return announcement.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AnnouncementDTO>> getAllAnnouncements() {
        List<AnnouncementDTO> announcements = announcementService.getAllAnnouncements();
        return ResponseEntity.ok(announcements);
    }

    @GetMapping("/active")
    public ResponseEntity<List<AnnouncementDTO>> getActiveAnnouncements() {
        List<AnnouncementDTO> announcements = announcementService.getActiveAnnouncements();
        return ResponseEntity.ok(announcements);
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<AnnouncementDTO>> getAnnouncementsByPriority(@PathVariable String priority) {
        List<AnnouncementDTO> announcements = announcementService.getAnnouncementsByPriority(priority.toUpperCase());
        return ResponseEntity.ok(announcements);
    }

    @GetMapping("/active-non-expired")
    public ResponseEntity<List<AnnouncementDTO>> getActiveNonExpiredAnnouncements() {
        List<AnnouncementDTO> announcements = announcementService.getActiveNonExpiredAnnouncements();
        return ResponseEntity.ok(announcements);
    }
}
