package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.UserDTO;
import com.chanrady.hrms.entity.User;
import com.chanrady.hrms.entity.Role;
import com.chanrady.hrms.repository.UserRepository;
import com.chanrady.hrms.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPasswordHash(userDTO.getPasswordHash());

        if (userDTO.getRoleId() != null) {
            Optional<Role> role = roleRepository.findById(userDTO.getRoleId());
            role.ifPresent(user::setRole);
        }

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(Integer id, UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setUsername(userDTO.getUsername());
            user.setFullName(userDTO.getFullName());
            user.setEmail(userDTO.getEmail());
            user.setPasswordHash(userDTO.getPasswordHash());

            if (userDTO.getRoleId() != null) {
                Optional<Role> role = roleRepository.findById(userDTO.getRoleId());
                role.ifPresent(user::setRole);
            }

            User updatedUser = userRepository.save(user);
            return convertToDTO(updatedUser);
        }
        return null;
    }

    @Override
    public void deleteUser(Integer id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(u -> {
            u.setDeletedAt(LocalDateTime.now());
            userRepository.save(u);
        });
    }

    @Override
    public Optional<UserDTO> getUserById(Integer id) {
        return userRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findByDeletedAtIsNull()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> getUserByUsername(String username) {
        return userRepository.findByUsername(username).map(this::convertToDTO);
    }

    @Override
    public Optional<UserDTO> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(this::convertToDTO);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPasswordHash(user.getPasswordHash());
        if (user.getRole() != null) {
            dto.setRoleId(user.getRole().getId());
            dto.setRoleName(user.getRole().getRoleName());
        }
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}

