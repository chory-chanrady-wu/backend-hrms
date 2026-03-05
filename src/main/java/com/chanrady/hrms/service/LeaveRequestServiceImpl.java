package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.LeaveRequestDTO;
import com.chanrady.hrms.entity.LeaveRequest;
import com.chanrady.hrms.entity.Employee;
import com.chanrady.hrms.entity.LeaveType;
import com.chanrady.hrms.repository.LeaveRequestRepository;
import com.chanrady.hrms.repository.EmployeeRepository;
import com.chanrady.hrms.repository.LeaveTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @Override
    public LeaveRequestDTO createLeaveRequest(LeaveRequestDTO leaveRequestDTO) {
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setStartDate(leaveRequestDTO.getStartDate());
        leaveRequest.setEndDate(leaveRequestDTO.getEndDate());
        leaveRequest.setReason(leaveRequestDTO.getReason());
        leaveRequest.setStatus(leaveRequestDTO.getStatus());

        if (leaveRequestDTO.getEmployeeId() != null) {
            Optional<Employee> employee = employeeRepository.findById(leaveRequestDTO.getEmployeeId());
            employee.ifPresent(leaveRequest::setEmployee);
        }

        if (leaveRequestDTO.getLeaveTypeId() != null) {
            Optional<LeaveType> leaveType = leaveTypeRepository.findById(leaveRequestDTO.getLeaveTypeId());
            leaveType.ifPresent(leaveRequest::setLeaveType);
        }

        LeaveRequest savedLeaveRequest = leaveRequestRepository.save(leaveRequest);
        return convertToDTO(savedLeaveRequest);
    }

    @Override
    public LeaveRequestDTO updateLeaveRequest(Integer id, LeaveRequestDTO leaveRequestDTO) {
        Optional<LeaveRequest> existingLeaveRequest = leaveRequestRepository.findById(id);
        if (existingLeaveRequest.isPresent()) {
            LeaveRequest leaveRequest = existingLeaveRequest.get();
            leaveRequest.setStartDate(leaveRequestDTO.getStartDate());
            leaveRequest.setEndDate(leaveRequestDTO.getEndDate());
            leaveRequest.setReason(leaveRequestDTO.getReason());
            leaveRequest.setStatus(leaveRequestDTO.getStatus());

            if (leaveRequestDTO.getEmployeeId() != null) {
                Optional<Employee> employee = employeeRepository.findById(leaveRequestDTO.getEmployeeId());
                employee.ifPresent(leaveRequest::setEmployee);
            }

            if (leaveRequestDTO.getLeaveTypeId() != null) {
                Optional<LeaveType> leaveType = leaveTypeRepository.findById(leaveRequestDTO.getLeaveTypeId());
                leaveType.ifPresent(leaveRequest::setLeaveType);
            }

            LeaveRequest updatedLeaveRequest = leaveRequestRepository.save(leaveRequest);
            return convertToDTO(updatedLeaveRequest);
        }
        return null;
    }

    @Override
    public void deleteLeaveRequest(Integer id) {
        Optional<LeaveRequest> leaveRequest = leaveRequestRepository.findById(id);
        leaveRequest.ifPresent(lr -> {
            lr.setDeletedAt(LocalDateTime.now());
            leaveRequestRepository.save(lr);
        });
    }

    @Override
    public Optional<LeaveRequestDTO> getLeaveRequestById(Integer id) {
        return leaveRequestRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public List<LeaveRequestDTO> getAllLeaveRequests() {
        return leaveRequestRepository.findByDeletedAtIsNull()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LeaveRequestDTO> getLeaveRequestsByEmployee(Integer employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            return leaveRequestRepository.findByEmployee(employee.get())
                    .stream()
                    .filter(lr -> lr.getDeletedAt() == null)
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    @Override
    public List<LeaveRequestDTO> getLeaveRequestsByStatus(String status) {
        return leaveRequestRepository.findByStatus(status)
                .stream()
                .filter(lr -> lr.getDeletedAt() == null)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private LeaveRequestDTO convertToDTO(LeaveRequest leaveRequest) {
        LeaveRequestDTO dto = new LeaveRequestDTO();
        dto.setId(leaveRequest.getId());
        dto.setStartDate(leaveRequest.getStartDate());
        dto.setEndDate(leaveRequest.getEndDate());
        dto.setReason(leaveRequest.getReason());
        dto.setStatus(leaveRequest.getStatus());
        if (leaveRequest.getEmployee() != null) {
            dto.setEmployeeId(leaveRequest.getEmployee().getId());
            dto.setEmployeeName(leaveRequest.getEmployee().getUser() != null ?
                    leaveRequest.getEmployee().getUser().getFullName() : "N/A");
        }
        if (leaveRequest.getLeaveType() != null) {
            dto.setLeaveTypeId(leaveRequest.getLeaveType().getId());
            dto.setLeaveTypeName(leaveRequest.getLeaveType().getName());
        }
        dto.setCreatedAt(leaveRequest.getCreatedAt());
        dto.setUpdatedAt(leaveRequest.getUpdatedAt());
        return dto;
    }
}

