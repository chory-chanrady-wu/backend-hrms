package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.LeaveRequestDTO;
import java.util.List;
import java.util.Optional;

public interface LeaveRequestService {
    LeaveRequestDTO createLeaveRequest(LeaveRequestDTO leaveRequestDTO);
    LeaveRequestDTO updateLeaveRequest(Integer id, LeaveRequestDTO leaveRequestDTO);
    void deleteLeaveRequest(Integer id);
    Optional<LeaveRequestDTO> getLeaveRequestById(Integer id);
    List<LeaveRequestDTO> getAllLeaveRequests();
    List<LeaveRequestDTO> getLeaveRequestsByEmployee(Integer employeeId);
    List<LeaveRequestDTO> getLeaveRequestsByStatus(String status);
}

