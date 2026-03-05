package com.chanrady.hrms.controller;

import com.chanrady.hrms.dto.LeaveRequestDTO;
import com.chanrady.hrms.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/leave-requests")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @PostMapping
    public ResponseEntity<LeaveRequestDTO> createLeaveRequest(@RequestBody LeaveRequestDTO leaveRequestDTO) {
        LeaveRequestDTO createdLeaveRequest = leaveRequestService.createLeaveRequest(leaveRequestDTO);
        return new ResponseEntity<>(createdLeaveRequest, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveRequestDTO> getLeaveRequestById(@PathVariable Integer id) {
        Optional<LeaveRequestDTO> leaveRequest = leaveRequestService.getLeaveRequestById(id);
        return leaveRequest.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<LeaveRequestDTO>> getAllLeaveRequests() {
        List<LeaveRequestDTO> leaveRequests = leaveRequestService.getAllLeaveRequests();
        return ResponseEntity.ok(leaveRequests);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LeaveRequestDTO>> getLeaveRequestsByEmployee(@PathVariable Integer employeeId) {
        List<LeaveRequestDTO> leaveRequests = leaveRequestService.getLeaveRequestsByEmployee(employeeId);
        return ResponseEntity.ok(leaveRequests);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<LeaveRequestDTO>> getLeaveRequestsByStatus(@PathVariable String status) {
        List<LeaveRequestDTO> leaveRequests = leaveRequestService.getLeaveRequestsByStatus(status);
        return ResponseEntity.ok(leaveRequests);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeaveRequestDTO> updateLeaveRequest(@PathVariable Integer id, @RequestBody LeaveRequestDTO leaveRequestDTO) {
        LeaveRequestDTO updatedLeaveRequest = leaveRequestService.updateLeaveRequest(id, leaveRequestDTO);
        if (updatedLeaveRequest != null) {
            return ResponseEntity.ok(updatedLeaveRequest);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeaveRequest(@PathVariable Integer id) {
        leaveRequestService.deleteLeaveRequest(id);
        return ResponseEntity.noContent().build();
    }
}

