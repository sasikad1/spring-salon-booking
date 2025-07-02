package com.sasika.salon.booking.repository;

import com.sasika.salon.booking.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    @Query("SELECT DISTINCT b FROM Branch b LEFT JOIN FETCH b.staffList LEFT JOIN FETCH b.workingHours")
    List<Branch> findAllWithStaffAndWorkingHours();


}
