package com.sasika.salon.booking.service.impl;

import com.sasika.salon.booking.entity.Branch;
import com.sasika.salon.booking.repository.BranchRepository;
import com.sasika.salon.booking.repository.CustomerRepository;
import com.sasika.salon.booking.service.BranchService;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@Service
public class BranchServiceImpl implements BranchService {
    private static final Logger logger = LoggerFactory.getLogger(BranchServiceImpl.class);

    private  final BranchRepository branchRepository;
    public BranchServiceImpl(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public List<Branch> getAllBranches(){
        logger.info("Fetching all branches");
        return branchRepository.findAll();
    }

    @Override
    public Branch getBranchById(Long id){
        logger.info("Find Branch by id {}", id);
        return branchRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Branch not found"+id));
    }

    public Branch createBranch(Branch branch){
        logger.info("Creating new branch {}", branch);
        return branchRepository.save(branch);
    }

    @Override
    public Branch updateBranch(Long id, Branch branch){
        logger.info("Update branch {}", branch);
        return branchRepository.findById(id)
                .map(existingBranch->{
                    existingBranch.setName(branch.getName());
                    existingBranch.setAddress(branch.getAddress());
                    existingBranch.setPhoneNumber(branch.getPhoneNumber());
                    existingBranch.setEmail(branch.getEmail());
                    existingBranch.setStaffList(branch.getStaffList());
                    existingBranch.setAppointments(branch.getAppointments());
                    return branchRepository.save(existingBranch);
                })
                .orElseThrow(()->{
                    logger.info("Branch not found {}", id);
                    return new RuntimeException("Branch not found with ID: " + id);
                });
    }

    @Override
    public String deleteBranch(Long id){
        logger.info("Delete branch {}", id);
        boolean existsBranch = branchRepository.existsById(id);
        if(!existsBranch){
            return "Branch not found with id "+id;
        }
        branchRepository.deleteById(id);
        return "Successfully deleted branch with id "+id;
    }

}
