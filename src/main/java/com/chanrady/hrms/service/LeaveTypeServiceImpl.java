package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.LeaveTypeDTO;
import com.chanrady.hrms.entity.LeaveType;
import com.chanrady.hrms.repository.LeaveTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeaveTypeServiceImpl implements LeaveTypeService {

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @Override
    public LeaveTypeDTO createLeaveType(LeaveTypeDTO leaveTypeDTO) {
        LeaveType leaveType = new LeaveType();
        leaveType.setName(leaveTypeDTO.getName());
        leaveType.setMaxDays(leaveTypeDTO.getMaxDays());
        LeaveType savedLeaveType = leaveTypeRepository.save(leaveType);
        return convertToDTO(savedLeaveType);
    }

    @Override
    public LeaveTypeDTO updateLeaveType(Integer id, LeaveTypeDTO leaveTypeDTO) {
        Optional<LeaveType> existingLeaveType = leaveTypeRepository.findById(id);
        if (existingLeaveType.isPresent()) {
            LeaveType leaveType = existingLeaveType.get();
            leaveType.setName(leaveTypeDTO.getName());
            leaveType.setMaxDays(leaveTypeDTO.getMaxDays());
            LeaveType updatedLeaveType = leaveTypeRepository.save(leaveType);
            return convertToDTO(updatedLeaveType);
        }
        return null;
    }

    @Override
    public void deleteLeaveType(Integer id) {
        Optional<LeaveType> leaveType = leaveTypeRepository.findById(id);
        leaveType.ifPresent(lt -> {
            lt.setDeletedAt(LocalDateTime.now());
            leaveTypeRepository.save(lt);
        });
    }

    @Override
    public Optional<LeaveTypeDTO> getLeaveTypeById(Integer id) {
        return leaveTypeRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public List<LeaveTypeDTO> getAllLeaveTypes() {
        return leaveTypeRepository.findByDeletedAtIsNull()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<LeaveTypeDTO> getLeaveTypeByName(String name) {
        return leaveTypeRepository.findByName(name).map(this::convertToDTO);
    }

    private LeaveTypeDTO convertToDTO(LeaveType leaveType) {
        LeaveTypeDTO dto = new LeaveTypeDTO();
        dto.setId(leaveType.getId());
        dto.setName(leaveType.getName());
        dto.setMaxDays(leaveType.getMaxDays());
        dto.setCreatedAt(leaveType.getCreatedAt());
        dto.setUpdatedAt(leaveType.getUpdatedAt());
        return dto;
    }
}

