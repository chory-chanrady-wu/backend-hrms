package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.PositionDTO;
import com.chanrady.hrms.entity.Position;
import com.chanrady.hrms.entity.Department;
import com.chanrady.hrms.repository.PositionRepository;
import com.chanrady.hrms.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public PositionDTO createPosition(PositionDTO positionDTO) {
        Position position = new Position();
        position.setTitle(positionDTO.getTitle());
        position.setDescription(positionDTO.getDescription());

        if (positionDTO.getDepartmentId() != null) {
            Optional<Department> department = departmentRepository.findById(positionDTO.getDepartmentId());
            department.ifPresent(position::setDepartment);
        }

        Position savedPosition = positionRepository.save(position);
        return convertToDTO(savedPosition);
    }

    @Override
    public PositionDTO updatePosition(Integer id, PositionDTO positionDTO) {
        Optional<Position> existingPosition = positionRepository.findById(id);
        if (existingPosition.isPresent()) {
            Position position = existingPosition.get();
            position.setTitle(positionDTO.getTitle());
            position.setDescription(positionDTO.getDescription());

            if (positionDTO.getDepartmentId() != null) {
                Optional<Department> department = departmentRepository.findById(positionDTO.getDepartmentId());
                department.ifPresent(position::setDepartment);
            }

            Position updatedPosition = positionRepository.save(position);
            return convertToDTO(updatedPosition);
        }
        return null;
    }

    @Override
    public void deletePosition(Integer id) {
        Optional<Position> position = positionRepository.findById(id);
        position.ifPresent(p -> {
            p.setDeletedAt(LocalDateTime.now());
            positionRepository.save(p);
        });
    }

    @Override
    public Optional<PositionDTO> getPositionById(Integer id) {
        return positionRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public List<PositionDTO> getAllPositions() {
        return positionRepository.findByDeletedAtIsNull()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PositionDTO> getPositionsByDepartment(Integer departmentId) {
        Optional<Department> department = departmentRepository.findById(departmentId);
        if (department.isPresent()) {
            return positionRepository.findByDepartment(department.get())
                    .stream()
                    .filter(p -> p.getDeletedAt() == null)
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    private PositionDTO convertToDTO(Position position) {
        PositionDTO dto = new PositionDTO();
        dto.setId(position.getId());
        dto.setTitle(position.getTitle());
        dto.setDescription(position.getDescription());
        if (position.getDepartment() != null) {
            dto.setDepartmentId(position.getDepartment().getId());
        }
        dto.setCreatedAt(position.getCreatedAt());
        dto.setUpdatedAt(position.getUpdatedAt());
        return dto;
    }
}

