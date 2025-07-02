package com.sasika.salon.booking.service.impl;

import com.sasika.salon.booking.entity.Branch;
import com.sasika.salon.booking.entity.Slot;
import com.sasika.salon.booking.entity.Staff;
import com.sasika.salon.booking.entity.WorkingHours;
import com.sasika.salon.booking.enums.SlotType;
import com.sasika.salon.booking.repository.BranchRepository;
import com.sasika.salon.booking.repository.SlotRepository;
import com.sasika.salon.booking.repository.StaffRepository;
import com.sasika.salon.booking.service.SlotService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SlotServiceImpl implements SlotService {

    @Autowired
    private BranchRepository branchRepo;
    @Autowired
    private StaffRepository staffRepo;
    @Autowired
    private SlotRepository slotRepo;

//    after first deployment of the app
    @Override
    public void initializeSlotsForFiveDays() {
        for (int i = 0; i < 5; i++) {
            generateSlotsForDate(LocalDate.now().plusDays(i));
        }
    }

//    dally work 10p.m this api
    @Override
    public void generateNextDaySlotAfterCycle() {
        LocalDate sixthDay = LocalDate.now().plusDays(5);
        generateSlotsForDate(sixthDay);
    }

    @Override
    public List<Slot> getSlotsByDate(LocalDate date) {
        return slotRepo.findBySlotDate(date);
    }


@Transactional
@Override
public String generateSlotsForDate(LocalDate date) {
//    List<Staff> staffMembers = staffRepo.findAll();
    List<Staff> staffMembers = staffRepo.findAllWithBranchAndWorkingHours();
    int totalSlotsSaved = 0;

    for (Staff staff : staffMembers) {
        System.out.println("🔍 Checking staff: " + staff.getName());
        Integer duration = staff.getSlotDurationInMinutes();

        Set<WorkingHours> workingHoursSet = staff.getBranch().getWorkingHours();

        for (WorkingHours workingHour : workingHoursSet) {
            if (workingHour.getDayOfWeek().equalsIgnoreCase(date.getDayOfWeek().name()) && !workingHour.isClosed()) {

                LocalTime startTime = workingHour.getOpenTime();
                LocalTime endTime = workingHour.getCloseTime();

                while (startTime.plusMinutes(duration).isBefore(endTime.plusSeconds(1))) {
                    LocalTime slotEnd = startTime.plusMinutes(duration);

                    Slot slot = Slot.builder()
                            .branch(staff.getBranch())
                            .staff(staff)
                            .startTime(startTime)
                            .endTime(slotEnd)
                            .slotDate(date)
                            .durationInMinutes(duration)
                            .available(true)
                            .slotType(SlotType.WORKING)
                            .build();

                    slotRepo.save(slot);
                    System.out.println("✅ Saved slot for " + staff.getName() + ": " + startTime + " - " + slotEnd);
                    totalSlotsSaved++;

                    startTime = slotEnd;
                }
                break;
            }
        }
    }

    return "✅ Total slots saved: " + totalSlotsSaved;
}


    @Override
    public void deleteSlotsForDate(LocalDate date) {
        slotRepo.deleteBySlotDate(date);
    }

    @Override
    public void deleteAllSlots() {
        slotRepo.deleteAll();
    }

}
