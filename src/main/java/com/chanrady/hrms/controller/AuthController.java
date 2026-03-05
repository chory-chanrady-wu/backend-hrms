package com.chanrady.hrms.controller;

import com.chanrady.hrms.dto.UserDTO;
import com.chanrady.hrms.security.JwtTokenProvider;
import com.chanrady.hrms.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Authentication endpoints for login and register")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * Register a new user
     */
    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Create a new user account with provided credentials")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input or user already exists"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
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

            if (userDTO.getPasswordHash() == null || userDTO.getPasswordHash().trim().isEmpty()) {
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
    @Operation(summary = "Login user", description = "Authenticate user with username/email and password")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login successful"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials"),
        @ApiResponse(responseCode = "400", description = "Missing required fields"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
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

            // Find user by username or email
            Optional<UserDTO> user;

            // Check if input looks like an email (contains @)
            if (usernameOrEmail.contains("@")) {
                user = userService.getUserByEmail(usernameOrEmail);
            } else {
                user = userService.getUserByUsername(usernameOrEmail);
            }

            // If not found, return unauthorized
            if (user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(createErrorResponse("Invalid username/email or password"));
            }

            UserDTO userDTO = user.get();

            // Validate password (simple comparison - in production, use proper password hashing)
            if (!userDTO.getPasswordHash().equals(password)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(createErrorResponse("Invalid username/email or password"));
            }

            // Login successful - return only JWT tokens
            String accessToken = jwtTokenProvider.generateAccessToken(userDTO);
            String refreshToken = jwtTokenProvider.generateRefreshToken(userDTO);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Login successful");
            response.put("accessToken", accessToken);
            response.put("refreshToken", refreshToken);
            response.put("tokenType", "Bearer");
            response.put("expiresIn", jwtTokenProvider.getTokenExpirationSeconds());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
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
