package com.chanrady.hrms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Integer id;
    private Integer userId;
    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String userStatus;
    private Integer departmentId;
    private String departmentName;
    private Integer positionId;
    private String positionName;
    private String employmentType;
    private BigDecimal salary;
    private LocalDate hireDate;
    private Boolean status;
    private String imageUrl;
    // Optional form-data file input (key: image)
    private MultipartFile image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
