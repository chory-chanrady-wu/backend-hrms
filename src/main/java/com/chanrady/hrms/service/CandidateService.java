package com.chanrady.hrms.service;

import com.chanrady.hrms.dto.CandidateDTO;
import java.util.List;
import java.util.UUID;

public interface CandidateService {
    CandidateDTO createCandidate(CandidateDTO candidateDTO);
    CandidateDTO getCandidateById(UUID id);
    List<CandidateDTO> getAllCandidates();
    CandidateDTO updateCandidate(UUID id, CandidateDTO candidateDTO);
    void deleteCandidate(UUID id);
}

