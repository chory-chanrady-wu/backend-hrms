package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.EmployeeBenefitDTO;
import com.chanrady.hrms.entity.EmployeeBenefit;
import com.chanrady.hrms.entity.Employee;
import com.chanrady.hrms.entity.Benefit;
import com.chanrady.hrms.repository.EmployeeBenefitRepository;
import com.chanrady.hrms.repository.EmployeeRepository;
import com.chanrady.hrms.repository.BenefitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeBenefitServiceImpl implements EmployeeBenefitService {

    @Autowired
    private EmployeeBenefitRepository employeeBenefitRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BenefitRepository benefitRepository;

    @Override
    public EmployeeBenefitDTO createEmployeeBenefit(EmployeeBenefitDTO employeeBenefitDTO) {
        EmployeeBenefit employeeBenefit = new EmployeeBenefit();
        employeeBenefit.setStartDate(employeeBenefitDTO.getStartDate());
        employeeBenefit.setEndDate(employeeBenefitDTO.getEndDate());

        if (employeeBenefitDTO.getEmployeeId() != null) {
            Optional<Employee> employee = employeeRepository.findById(employeeBenefitDTO.getEmployeeId());
            employee.ifPresent(employeeBenefit::setEmployee);
        }

        if (employeeBenefitDTO.getBenefitId() != null) {
            Optional<Benefit> benefit = benefitRepository.findById(employeeBenefitDTO.getBenefitId());
            benefit.ifPresent(employeeBenefit::setBenefit);
        }

        EmployeeBenefit savedEmployeeBenefit = employeeBenefitRepository.save(employeeBenefit);
        return convertToDTO(savedEmployeeBenefit);
    }

    @Override
    public EmployeeBenefitDTO updateEmployeeBenefit(Integer id, EmployeeBenefitDTO employeeBenefitDTO) {
        Optional<EmployeeBenefit> existingEmployeeBenefit = employeeBenefitRepository.findById(id);
        if (existingEmployeeBenefit.isPresent()) {
            EmployeeBenefit employeeBenefit = existingEmployeeBenefit.get();
            employeeBenefit.setStartDate(employeeBenefitDTO.getStartDate());
            employeeBenefit.setEndDate(employeeBenefitDTO.getEndDate());

            if (employeeBenefitDTO.getEmployeeId() != null) {
                Optional<Employee> employee = employeeRepository.findById(employeeBenefitDTO.getEmployeeId());
                employee.ifPresent(employeeBenefit::setEmployee);
            }

            if (employeeBenefitDTO.getBenefitId() != null) {
                Optional<Benefit> benefit = benefitRepository.findById(employeeBenefitDTO.getBenefitId());
                benefit.ifPresent(employeeBenefit::setBenefit);
            }

            EmployeeBenefit updatedEmployeeBenefit = employeeBenefitRepository.save(employeeBenefit);
            return convertToDTO(updatedEmployeeBenefit);
        }
        return null;
    }

    @Override
    public void deleteEmployeeBenefit(Integer id) {
        Optional<EmployeeBenefit> employeeBenefit = employeeBenefitRepository.findById(id);
        employeeBenefit.ifPresent(eb -> {
            eb.setDeletedAt(LocalDateTime.now());
            employeeBenefitRepository.save(eb);
        });
    }

    @Override
    public Optional<EmployeeBenefitDTO> getEmployeeBenefitById(Integer id) {
        return employeeBenefitRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public List<EmployeeBenefitDTO> getAllEmployeeBenefits() {
        return employeeBenefitRepository.findByDeletedAtIsNull()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeBenefitDTO> getEmployeeBenefitsByEmployee(Integer employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            return employeeBenefitRepository.findByEmployee(employee.get())
                    .stream()
                    .filter(eb -> eb.getDeletedAt() == null)
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    private EmployeeBenefitDTO convertToDTO(EmployeeBenefit employeeBenefit) {
        EmployeeBenefitDTO dto = new EmployeeBenefitDTO();
        dto.setId(employeeBenefit.getId());
        dto.setStartDate(employeeBenefit.getStartDate());
        dto.setEndDate(employeeBenefit.getEndDate());
        if (employeeBenefit.getEmployee() != null) {
            dto.setEmployeeId(employeeBenefit.getEmployee().getId());
            dto.setEmployeeName(employeeBenefit.getEmployee().getUser() != null ?
                    employeeBenefit.getEmployee().getUser().getFullName() : "N/A");
        }
        if (employeeBenefit.getBenefit() != null) {
            dto.setBenefitId(employeeBenefit.getBenefit().getId());
            dto.setBenefitName(employeeBenefit.getBenefit().getName());
        }
        dto.setCreatedAt(employeeBenefit.getCreatedAt());
        dto.setUpdatedAt(employeeBenefit.getUpdatedAt());
        return dto;
    }
}

