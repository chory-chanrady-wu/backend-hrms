package com.chanrady.hrms.controller;

import com.chanrady.hrms.dto.AnnouncementDTO;
import com.chanrady.hrms.service.AnnouncementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Announcements", description = "Announcement management endpoints")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @PostMapping
    @Operation(summary = "Create new announcement", description = "Create a new announcement")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Announcement created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<AnnouncementDTO> createAnnouncement(@RequestBody AnnouncementDTO announcementDTO) {
        AnnouncementDTO created = announcementService.createAnnouncement(announcementDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update announcement", description = "Update an existing announcement by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Announcement updated successfully"),
        @ApiResponse(responseCode = "404", description = "Announcement not found")
    })
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
    @Operation(summary = "Delete announcement", description = "Soft delete an announcement by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Announcement deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Announcement not found")
    })
    public ResponseEntity<Map<String, String>> deleteAnnouncement(@PathVariable Integer id) {
        announcementService.deleteAnnouncement(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Announcement deleted successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get announcement by ID", description = "Retrieve a specific announcement by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Announcement found"),
        @ApiResponse(responseCode = "404", description = "Announcement not found")
    })
    public ResponseEntity<AnnouncementDTO> getAnnouncementById(@PathVariable Integer id) {
        Optional<AnnouncementDTO> announcement = announcementService.getAnnouncementById(id);
        return announcement.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get all announcements", description = "Retrieve all non-deleted announcements")
    @ApiResponse(responseCode = "200", description = "Announcements retrieved successfully")
    public ResponseEntity<List<AnnouncementDTO>> getAllAnnouncements() {
        List<AnnouncementDTO> announcements = announcementService.getAllAnnouncements();
        return ResponseEntity.ok(announcements);
    }

    @GetMapping("/active")
    @Operation(summary = "Get active announcements", description = "Retrieve all active announcements")
    @ApiResponse(responseCode = "200", description = "Active announcements retrieved successfully")
    public ResponseEntity<List<AnnouncementDTO>> getActiveAnnouncements() {
        List<AnnouncementDTO> announcements = announcementService.getActiveAnnouncements();
        return ResponseEntity.ok(announcements);
    }

    @GetMapping("/priority/{priority}")
    @Operation(summary = "Get announcements by priority", description = "Retrieve announcements by priority level (LOW, MEDIUM, HIGH, URGENT)")
    @ApiResponse(responseCode = "200", description = "Announcements retrieved successfully")
    public ResponseEntity<List<AnnouncementDTO>> getAnnouncementsByPriority(@PathVariable String priority) {
        List<AnnouncementDTO> announcements = announcementService.getAnnouncementsByPriority(priority.toUpperCase());
        return ResponseEntity.ok(announcements);
    }

    @GetMapping("/active-non-expired")
    @Operation(summary = "Get active non-expired announcements", description = "Retrieve active announcements that haven't expired")
    @ApiResponse(responseCode = "200", description = "Active non-expired announcements retrieved successfully")
    public ResponseEntity<List<AnnouncementDTO>> getActiveNonExpiredAnnouncements() {
        List<AnnouncementDTO> announcements = announcementService.getActiveNonExpiredAnnouncements();
        return ResponseEntity.ok(announcements);
    }
}

