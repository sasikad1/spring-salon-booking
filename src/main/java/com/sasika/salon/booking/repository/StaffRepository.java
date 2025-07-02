package com.sasika.salon.booking.repository;

import com.sasika.salon.booking.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    @Query("SELECT s FROM Staff s " +
            "JOIN FETCH s.branch b " +
            "JOIN FETCH b.workingHours")
    List<Staff> findAllWithBranchAndWorkingHours();

}
