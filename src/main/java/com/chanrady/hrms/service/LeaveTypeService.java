package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.LeaveTypeDTO;
import java.util.List;
import java.util.Optional;

public interface LeaveTypeService {
    LeaveTypeDTO createLeaveType(LeaveTypeDTO leaveTypeDTO);
    LeaveTypeDTO updateLeaveType(Integer id, LeaveTypeDTO leaveTypeDTO);
    void deleteLeaveType(Integer id);
    Optional<LeaveTypeDTO> getLeaveTypeById(Integer id);
    List<LeaveTypeDTO> getAllLeaveTypes();
    Optional<LeaveTypeDTO> getLeaveTypeByName(String name);
}

