package com.chanrady.hrms.controller;

import com.chanrady.hrms.dto.AttendanceDTO;
import com.chanrady.hrms.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/attendance")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping
    public ResponseEntity<AttendanceDTO> createAttendance(@RequestBody AttendanceDTO attendanceDTO) {
        AttendanceDTO createdAttendance = attendanceService.createAttendance(attendanceDTO);
        return new ResponseEntity<>(createdAttendance, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceDTO> getAttendanceById(@PathVariable Integer id) {
        Optional<AttendanceDTO> attendance = attendanceService.getAttendanceById(id);
        return attendance.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AttendanceDTO>> getAllAttendance() {
        List<AttendanceDTO> attendances = attendanceService.getAllAttendance();
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByEmployee(@PathVariable Integer employeeId) {
        List<AttendanceDTO> attendances = attendanceService.getAttendanceByEmployee(employeeId);
        return ResponseEntity.ok(attendances);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttendanceDTO> updateAttendance(@PathVariable Integer id, @RequestBody AttendanceDTO attendanceDTO) {
        AttendanceDTO updatedAttendance = attendanceService.updateAttendance(id, attendanceDTO);
        if (updatedAttendance != null) {
            return ResponseEntity.ok(updatedAttendance);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Integer id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }
}

