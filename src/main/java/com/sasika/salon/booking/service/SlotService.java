package com.sasika.salon.booking.service;


import com.sasika.salon.booking.entity.Slot;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface SlotService {

    List<Slot> getSlotsByDate(LocalDate date);

    /**
     * Generate slots for a specific date for all staff/branches.
     */
    String generateSlotsForDate(LocalDate date);

    /**
     * Initialize slots for the first 5 days (run on app startup).
     */
    void initializeSlotsForFiveDays();

    /**
     * Generate slots for the 6th day ahead from today (called daily at 10PM).
     */
    void generateNextDaySlotAfterCycle();

    /**
     * Delete all slots for a given past date (e.g., yesterday).
     */
    void deleteSlotsForDate(LocalDate date);

    void deleteAllSlots();
}
