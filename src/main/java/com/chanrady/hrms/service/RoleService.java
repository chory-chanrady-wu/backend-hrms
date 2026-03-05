package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.RoleDTO;
import java.util.List;
import java.util.Optional;

public interface RoleService {
    RoleDTO createRole(RoleDTO roleDTO);
    RoleDTO updateRole(Integer id, RoleDTO roleDTO);
    void deleteRole(Integer id);
    Optional<RoleDTO> getRoleById(Integer id);
    List<RoleDTO> getAllRoles();
    Optional<RoleDTO> getRoleByName(String roleName);
}

