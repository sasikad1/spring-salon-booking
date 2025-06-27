package com.sasika.salon.booking.service;

import com.sasika.salon.booking.entity.Branch;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BranchService {
    List<Branch> getAllBranches();
    Branch getBranchById(Long id);
    Branch createBranch(Branch branch);
    Branch updateBranch(Long id, Branch branch);
    String deleteBranch(Long id);
}
