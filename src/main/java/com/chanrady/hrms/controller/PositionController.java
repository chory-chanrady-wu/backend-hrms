package com.chanrady.hrms.controller;

import com.chanrady.hrms.dto.PositionDTO;
import com.chanrady.hrms.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/positions")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PositionController {

    @Autowired
    private PositionService positionService;

    @PostMapping
    public ResponseEntity<PositionDTO> createPosition(@RequestBody PositionDTO positionDTO) {
        PositionDTO createdPosition = positionService.createPosition(positionDTO);
        return new ResponseEntity<>(createdPosition, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PositionDTO> getPositionById(@PathVariable Integer id) {
        Optional<PositionDTO> position = positionService.getPositionById(id);
        return position.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PositionDTO>> getAllPositions() {
        List<PositionDTO> positions = positionService.getAllPositions();
        return ResponseEntity.ok(positions);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<PositionDTO>> getPositionsByDepartment(@PathVariable Integer departmentId) {
        List<PositionDTO> positions = positionService.getPositionsByDepartment(departmentId);
        return ResponseEntity.ok(positions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PositionDTO> updatePosition(@PathVariable Integer id, @RequestBody PositionDTO positionDTO) {
        PositionDTO updatedPosition = positionService.updatePosition(id, positionDTO);
        if (updatedPosition != null) {
            return ResponseEntity.ok(updatedPosition);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePosition(@PathVariable Integer id) {
        positionService.deletePosition(id);
        return ResponseEntity.noContent().build();
    }
}

