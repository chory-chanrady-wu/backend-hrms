package com.chanrady.hrms.controller;

import com.chanrady.hrms.dto.LeaveTypeDTO;
import com.chanrady.hrms.service.LeaveTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/leave-types")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LeaveTypeController {

    @Autowired
    private LeaveTypeService leaveTypeService;

    @PostMapping
    public ResponseEntity<LeaveTypeDTO> createLeaveType(@RequestBody LeaveTypeDTO leaveTypeDTO) {
        LeaveTypeDTO createdLeaveType = leaveTypeService.createLeaveType(leaveTypeDTO);
        return new ResponseEntity<>(createdLeaveType, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveTypeDTO> getLeaveTypeById(@PathVariable Integer id) {
        Optional<LeaveTypeDTO> leaveType = leaveTypeService.getLeaveTypeById(id);
        return leaveType.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<LeaveTypeDTO>> getAllLeaveTypes() {
        List<LeaveTypeDTO> leaveTypes = leaveTypeService.getAllLeaveTypes();
        return ResponseEntity.ok(leaveTypes);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<LeaveTypeDTO> getLeaveTypeByName(@PathVariable String name) {
        Optional<LeaveTypeDTO> leaveType = leaveTypeService.getLeaveTypeByName(name);
        return leaveType.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeaveTypeDTO> updateLeaveType(@PathVariable Integer id, @RequestBody LeaveTypeDTO leaveTypeDTO) {
        LeaveTypeDTO updatedLeaveType = leaveTypeService.updateLeaveType(id, leaveTypeDTO);
        if (updatedLeaveType != null) {
            return ResponseEntity.ok(updatedLeaveType);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeaveType(@PathVariable Integer id) {
        leaveTypeService.deleteLeaveType(id);
        return ResponseEntity.noContent().build();
    }
}

