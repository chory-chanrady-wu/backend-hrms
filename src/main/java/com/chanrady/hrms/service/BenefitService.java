package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.BenefitDTO;
import java.util.List;
import java.util.Optional;

public interface BenefitService {
    BenefitDTO createBenefit(BenefitDTO benefitDTO);
    BenefitDTO updateBenefit(Integer id, BenefitDTO benefitDTO);
    void deleteBenefit(Integer id);
    Optional<BenefitDTO> getBenefitById(Integer id);
    List<BenefitDTO> getAllBenefits();
    Optional<BenefitDTO> getBenefitByName(String name);
}

