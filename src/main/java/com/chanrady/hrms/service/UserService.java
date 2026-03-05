package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.UserDTO;
import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(Integer id, UserDTO userDTO);
    void deleteUser(Integer id);
    Optional<UserDTO> getUserById(Integer id);
    List<UserDTO> getAllUsers();
    Optional<UserDTO> getUserByUsername(String username);
    Optional<UserDTO> getUserByEmail(String email);
}

