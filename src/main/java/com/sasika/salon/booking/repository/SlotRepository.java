package com.sasika.salon.booking.repository;

import com.sasika.salon.booking.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface SlotRepository extends JpaRepository<Slot, Long> {
    // This allows deletion of all slots by a specific date
    void deleteBySlotDate(LocalDate date);

    List<Slot> findBySlotDate(LocalDate date);

    boolean existsBySlotDateAndStaffIdAndBranchId(LocalDate date, Long staffId, Long branchId);


}
