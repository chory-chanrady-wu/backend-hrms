package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.BenefitDTO;
import com.chanrady.hrms.entity.Benefit;
import com.chanrady.hrms.repository.BenefitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BenefitServiceImpl implements BenefitService {

    @Autowired
    private BenefitRepository benefitRepository;

    @Override
    public BenefitDTO createBenefit(BenefitDTO benefitDTO) {
        Benefit benefit = new Benefit();
        benefit.setName(benefitDTO.getName());
        benefit.setDescription(benefitDTO.getDescription());
        Benefit savedBenefit = benefitRepository.save(benefit);
        return convertToDTO(savedBenefit);
    }

    @Override
    public BenefitDTO updateBenefit(Integer id, BenefitDTO benefitDTO) {
        Optional<Benefit> existingBenefit = benefitRepository.findById(id);
        if (existingBenefit.isPresent()) {
            Benefit benefit = existingBenefit.get();
            benefit.setName(benefitDTO.getName());
            benefit.setDescription(benefitDTO.getDescription());
            Benefit updatedBenefit = benefitRepository.save(benefit);
            return convertToDTO(updatedBenefit);
        }
        return null;
    }

    @Override
    public void deleteBenefit(Integer id) {
        Optional<Benefit> benefit = benefitRepository.findById(id);
        benefit.ifPresent(b -> {
            b.setDeletedAt(LocalDateTime.now());
            benefitRepository.save(b);
        });
    }

    @Override
    public Optional<BenefitDTO> getBenefitById(Integer id) {
        return benefitRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public List<BenefitDTO> getAllBenefits() {
        return benefitRepository.findByDeletedAtIsNull()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BenefitDTO> getBenefitByName(String name) {
        return benefitRepository.findByName(name).map(this::convertToDTO);
    }

    private BenefitDTO convertToDTO(Benefit benefit) {
        BenefitDTO dto = new BenefitDTO();
        dto.setId(benefit.getId());
        dto.setName(benefit.getName());
        dto.setDescription(benefit.getDescription());
        dto.setCreatedAt(benefit.getCreatedAt());
        dto.setUpdatedAt(benefit.getUpdatedAt());
        return dto;
    }
}

