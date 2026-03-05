package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.RoleDTO;
import com.chanrady.hrms.entity.Role;
import com.chanrady.hrms.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = new Role();
        role.setRoleName(roleDTO.getRoleName());
        role.setPermissions(roleDTO.getPermissions());
        Role savedRole = roleRepository.save(role);
        return convertToDTO(savedRole);
    }

    @Override
    public RoleDTO updateRole(Integer id, RoleDTO roleDTO) {
        Optional<Role> existingRole = roleRepository.findById(id);
        if (existingRole.isPresent()) {
            Role role = existingRole.get();
            role.setRoleName(roleDTO.getRoleName());
            role.setPermissions(roleDTO.getPermissions());
            Role updatedRole = roleRepository.save(role);
            return convertToDTO(updatedRole);
        }
        return null;
    }

    @Override
    public void deleteRole(Integer id) {
        Optional<Role> role = roleRepository.findById(id);
        role.ifPresent(r -> {
            r.setDeletedAt(LocalDateTime.now());
            roleRepository.save(r);
        });
    }

    @Override
    public Optional<RoleDTO> getRoleById(Integer id) {
        return roleRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        return roleRepository.findByDeletedAtIsNull()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RoleDTO> getRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName).map(this::convertToDTO);
    }

    private RoleDTO convertToDTO(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setRoleName(role.getRoleName());
        dto.setPermissions(role.getPermissions());
        dto.setCreatedAt(role.getCreatedAt());
        dto.setUpdatedAt(role.getUpdatedAt());
        return dto;
    }
}

