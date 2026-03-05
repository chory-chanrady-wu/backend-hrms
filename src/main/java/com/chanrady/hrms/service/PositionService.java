package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.PositionDTO;
import java.util.List;
import java.util.Optional;

public interface PositionService {
    PositionDTO createPosition(PositionDTO positionDTO);
    PositionDTO updatePosition(Integer id, PositionDTO positionDTO);
    void deletePosition(Integer id);
    Optional<PositionDTO> getPositionById(Integer id);
    List<PositionDTO> getAllPositions();
    List<PositionDTO> getPositionsByDepartment(Integer departmentId);
}

