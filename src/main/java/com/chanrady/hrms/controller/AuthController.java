package com.chanrady.hrms.controller;

import com.chanrady.hrms.dto.UserDTO;
import com.chanrady.hrms.dto.EmployeeDTO;
import com.chanrady.hrms.entity.User;
import com.chanrady.hrms.repository.UserRepository;
import com.chanrady.hrms.security.JwtTokenProvider;
import com.chanrady.hrms.service.UserService;
import com.chanrady.hrms.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.hibernate.ObjectNotFoundException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Register a new user
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody UserDTO userDTO) {
        try {
            // Validate input
            if (userDTO.getUsername() == null || userDTO.getUsername().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(createErrorResponse("Username is required"));
            }

            if (userDTO.getEmail() == null || userDTO.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(createErrorResponse("Email is required"));
            }

            if (userDTO.getPassword() == null || userDTO.getPassword().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(createErrorResponse("Password is required"));
            }

            // Check if username already exists
            Optional<UserDTO> existingUserByUsername = userService.getUserByUsername(userDTO.getUsername());
            if (existingUserByUsername.isPresent()) {
                return ResponseEntity.badRequest()
                    .body(createErrorResponse("Username already exists"));
            }

            // Check if email already exists
            Optional<UserDTO> existingUserByEmail = userService.getUserByEmail(userDTO.getEmail());
            if (existingUserByEmail.isPresent()) {
                return ResponseEntity.badRequest()
                    .body(createErrorResponse("Email already exists"));
            }

            // Set default role (EMPLOYEE) if not provided
            if (userDTO.getRoleId() == null) {
                userDTO.setRoleId(4); // EMPLOYEE role ID (from seed data)
            }

            // Create new user
            UserDTO createdUser = userService.createUser(userDTO);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "User registered successfully");
            response.put("data", createdUser);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("Registration failed: " + e.getMessage()));
        }
    }

    /**
     * Login user
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        try {
            // Get username or email (can be either)
            String usernameOrEmail = loginRequest.get("username");
            if (usernameOrEmail == null || usernameOrEmail.trim().isEmpty()) {
                usernameOrEmail = loginRequest.get("email");
            }

            String password = loginRequest.get("password");

            // Validate input
            if (usernameOrEmail == null || usernameOrEmail.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(createErrorResponse("Username or email is required"));
            }

            if (password == null || password.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(createErrorResponse("Password is required"));
            }

            // Find user entity by username or email for password verification
            Optional<User> userEntity;
            if (usernameOrEmail.contains("@")) {
                userEntity = userRepository.findByEmail(usernameOrEmail);
            } else {
                userEntity = userRepository.findByUsername(usernameOrEmail);
            }

            // If not found, return unauthorized
            if (userEntity.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(createErrorResponse("Invalid username/email or password"));
            }

            User foundUser = userEntity.get();

            // Validate password using BCrypt
            if (!passwordEncoder.matches(password, foundUser.getPasswordHash())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(createErrorResponse("Invalid username/email or password"));
            }

            // Build response user DTO (without password)
            Optional<UserDTO> user = userService.getUserById(foundUser.getId());
            if (user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(createErrorResponse("Invalid username/email or password"));
            }

            UserDTO userDTO = user.get();

            // Login successful - generate tokens
            String accessToken = jwtTokenProvider.generateAccessToken(userDTO);
            String refreshToken = jwtTokenProvider.generateRefreshToken(userDTO);

            // Prepare sanitized user info (do not expose passwordHash)
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", userDTO.getId());
            userInfo.put("username", userDTO.getUsername());
            userInfo.put("fullName", userDTO.getFullName());
            userInfo.put("email", userDTO.getEmail());
            userInfo.put("phoneNumber", userDTO.getPhoneNumber());
            userInfo.put("status", userDTO.getStatus());
            userInfo.put("roleId", userDTO.getRoleId());
            userInfo.put("roleName", userDTO.getRoleName());
            userInfo.put("createdAt", userDTO.getCreatedAt());
            userInfo.put("updatedAt", userDTO.getUpdatedAt());

            // Try to get employee details
            Optional<EmployeeDTO> employee = Optional.empty();
            try {
                employee = employeeService.getEmployeeByUserId(userDTO.getId());
            } catch (ObjectNotFoundException ex) {
                // Employee references a missing user, log and continue
            }
            if (employee.isPresent()) {
                EmployeeDTO emp = employee.get();
                userInfo.put("employeeId", emp.getId());
                userInfo.put("departmentId", emp.getDepartmentId());
                userInfo.put("departmentName", emp.getDepartmentName());
                userInfo.put("positionId", emp.getPositionId());
                userInfo.put("positionName", emp.getPositionName());
                userInfo.put("employmentType", emp.getEmploymentType());
                userInfo.put("salary", emp.getSalary());
                userInfo.put("hireDate", emp.getHireDate());
                userInfo.put("status", emp.getStatus());
                userInfo.put("imageUrl", emp.getImageUrl());
            }

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Login successful");
            response.put("accessToken", accessToken);
            response.put("refreshToken", refreshToken);
            response.put("tokenType", "Bearer");
            response.put("expiresIn", jwtTokenProvider.getTokenExpirationSeconds());
            response.put("user", userInfo);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            if (e instanceof ObjectNotFoundException) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(createErrorResponse("Invalid username/email or password (user reference missing)"));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("Login failed: " + e.getMessage()));
        }
    }


    /**
     * Helper method to create error response
     */
    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        response.put("status", HttpStatus.BAD_REQUEST.value());
        return response;
    }
}
