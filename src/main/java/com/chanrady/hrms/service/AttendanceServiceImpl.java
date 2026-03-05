package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.AttendanceDTO;
import com.chanrady.hrms.entity.Attendance;
import com.chanrady.hrms.entity.Employee;
import com.chanrady.hrms.repository.AttendanceRepository;
import com.chanrady.hrms.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public AttendanceDTO createAttendance(AttendanceDTO attendanceDTO) {
        Attendance attendance = new Attendance();
        attendance.setCheckIn(attendanceDTO.getCheckIn());
        attendance.setCheckOut(attendanceDTO.getCheckOut());
        attendance.setStatus(attendanceDTO.getStatus());

        if (attendanceDTO.getEmployeeId() != null) {
            Optional<Employee> employee = employeeRepository.findById(attendanceDTO.getEmployeeId());
            employee.ifPresent(attendance::setEmployee);
        }

        Attendance savedAttendance = attendanceRepository.save(attendance);
        return convertToDTO(savedAttendance);
    }

    @Override
    public AttendanceDTO updateAttendance(Integer id, AttendanceDTO attendanceDTO) {
        Optional<Attendance> existingAttendance = attendanceRepository.findById(id);
        if (existingAttendance.isPresent()) {
            Attendance attendance = existingAttendance.get();
            attendance.setCheckIn(attendanceDTO.getCheckIn());
            attendance.setCheckOut(attendanceDTO.getCheckOut());
            attendance.setStatus(attendanceDTO.getStatus());

            if (attendanceDTO.getEmployeeId() != null) {
                Optional<Employee> employee = employeeRepository.findById(attendanceDTO.getEmployeeId());
                employee.ifPresent(attendance::setEmployee);
            }

            Attendance updatedAttendance = attendanceRepository.save(attendance);
            return convertToDTO(updatedAttendance);
        }
        return null;
    }

    @Override
    public void deleteAttendance(Integer id) {
        Optional<Attendance> attendance = attendanceRepository.findById(id);
        attendance.ifPresent(a -> {
            a.setDeletedAt(LocalDateTime.now());
            attendanceRepository.save(a);
        });
    }

    @Override
    public Optional<AttendanceDTO> getAttendanceById(Integer id) {
        return attendanceRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public List<AttendanceDTO> getAllAttendance() {
        return attendanceRepository.findByDeletedAtIsNull()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttendanceDTO> getAttendanceByEmployee(Integer employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            return attendanceRepository.findByEmployee(employee.get())
                    .stream()
                    .filter(a -> a.getDeletedAt() == null)
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    private AttendanceDTO convertToDTO(Attendance attendance) {
        AttendanceDTO dto = new AttendanceDTO();
        dto.setId(attendance.getId());
        dto.setCheckIn(attendance.getCheckIn());
        dto.setCheckOut(attendance.getCheckOut());
        dto.setStatus(attendance.getStatus());
        if (attendance.getEmployee() != null) {
            dto.setEmployeeId(attendance.getEmployee().getId());
            dto.setEmployeeName(attendance.getEmployee().getUser() != null ?
                    attendance.getEmployee().getUser().getFullName() : "N/A");
        }
        dto.setCreatedAt(attendance.getCreatedAt());
        dto.setUpdatedAt(attendance.getUpdatedAt());
        return dto;
    }
}

