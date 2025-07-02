package com.sasika.salon.booking.service.impl;

import com.sasika.salon.booking.entity.Branch;
import com.sasika.salon.booking.entity.WorkingHours;
import com.sasika.salon.booking.repository.BranchRepository;
import com.sasika.salon.booking.service.BranchService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchServiceImpl implements BranchService {
    private static final Logger logger = LoggerFactory.getLogger(BranchServiceImpl.class);

    private final BranchRepository branchRepository;
    private final ModelMapper modelMapper;

    public BranchServiceImpl(BranchRepository branchRepository, ModelMapper modelMapper) {
        this.branchRepository = branchRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Branch> getAllBranches() {
        logger.info("Fetching all branches");
        return branchRepository.findAll();
    }

    @Override
    public Branch getBranchById(Long id) {
        logger.info("Finding branch by ID: {}", id);
        return branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found with ID: " + id));
    }

    @Override
    public Branch createBranch(Branch branch) {
        logger.info("Creating new branch: {}", branch);

        if (branch.getWorkingHours() != null) {
            branch.getWorkingHours().forEach(wh -> wh.setBranch(branch)); // Set back-reference
        }

        return branchRepository.save(branch);
    }

    @Override
    public Branch updateBranch(Long id, Branch newBranchData) {
        return branchRepository.findById(id).map(existing -> {

            existing.setName(newBranchData.getName());
            existing.setAddress(newBranchData.getAddress());
            existing.setPhoneNumber(newBranchData.getPhoneNumber());
            existing.setEmail(newBranchData.getEmail());

            // FIX orphanRemoval issue by mutating the collection
            existing.getWorkingHours().clear();
            for (WorkingHours wh : newBranchData.getWorkingHours()) {
                wh.setBranch(existing); // set owning side
                existing.getWorkingHours().add(wh);
            }

            return branchRepository.save(existing);
        }).orElseThrow(() -> new EntityNotFoundException("Branch not found"));
    }



    @Override
    public String deleteBranch(Long id) {
        logger.info("Deleting branch with ID: {}", id);
        if (!branchRepository.existsById(id)) {
            return "Branch not found with ID: " + id;
        }
        branchRepository.deleteById(id);
        return "Successfully deleted branch with ID: " + id;
    }
}
