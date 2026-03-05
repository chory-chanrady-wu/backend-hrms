package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.AttendanceDTO;
import java.util.List;
import java.util.Optional;

public interface AttendanceService {
    AttendanceDTO createAttendance(AttendanceDTO attendanceDTO);
    AttendanceDTO updateAttendance(Integer id, AttendanceDTO attendanceDTO);
    void deleteAttendance(Integer id);
    Optional<AttendanceDTO> getAttendanceById(Integer id);
    List<AttendanceDTO> getAllAttendance();
    List<AttendanceDTO> getAttendanceByEmployee(Integer employeeId);
}

