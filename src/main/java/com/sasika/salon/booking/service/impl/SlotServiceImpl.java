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
    List<Staff> staffMembers = staffRepo.findAll();
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


    //    create slots
//    @Transactional
//    @Override
//    public String generateSlotsForDate(LocalDate date) {
//        StringBuilder result = new StringBuilder();
//        String dayOfWeek = date.getDayOfWeek().name();
//
//        List<Branch> branches = branchRepo.findAllWithStaffAndWorkingHours();
//        result.append("Branches found: ").append(branches.size()).append("\n");
//
//        for (Branch branch : branches) {
//            // Log working hours
//            result.append("→ Working Days for Branch '").append(branch.getName()).append("':\n");
//            branch.getWorkingHours().forEach(wh -> {
//                result.append("   - ")
//                        .append(wh.getDayOfWeek())
//                        .append(" | Open: ").append(wh.getOpenTime())
//                        .append(" - ").append(wh.getCloseTime())
//                        .append(" | Closed: ").append(wh.isClosed())
//                        .append("\n");
//            });
//
//            // Check if branch is open on this day
//            var optionalWorkingHour = branch.getWorkingHours()
//                    .stream()
//                    .filter(w -> w.getDayOfWeek().equalsIgnoreCase(dayOfWeek) && !w.isClosed())
//                    .findFirst();
//
//            if (optionalWorkingHour.isEmpty()) {
//                result.append("⚠️  Branch '").append(branch.getName()).append("' is closed on ").append(dayOfWeek).append("\n");
//                continue;
//            }
//
//            LocalTime openTime = optionalWorkingHour.get().getOpenTime();
//            LocalTime closeTime = optionalWorkingHour.get().getCloseTime();
//
//            List<Staff> staffList = branch.getStaffList();
//            result.append("Branch: ").append(branch.getName()).append(" | Staff count: ").append(staffList.size()).append("\n");
//
//            for (Staff staff : staffList) {
//                int slotDuration = staff.getSlotDurationInMinutes();
//
//                if (slotDuration <= 0) {
//                    result.append("⚠️  Invalid slot duration for staff ").append(staff.getName()).append("\n");
//                    continue;
//                }
//
//                // Check if slots already exist for this staff and date to prevent duplicates
//                boolean slotsExist = slotRepo.existsBySlotDateAndStaffIdAndBranchId(date, staff.getId(), branch.getId());
//
//                if (slotsExist) {
//                    result.append("⚠️  Slots already exist for staff ")
//                            .append(staff.getName())
//                            .append(" in branch ").append(branch.getName())
//                            .append(" on ").append(date).append("\n");
//                    continue;
//                }
//
//
//
//                // Start generating slots
//                LocalTime currentTime = openTime;
//                int count = 0;
//                while (currentTime.plusMinutes(slotDuration).isBefore(closeTime.plusSeconds(1))) {
//                    Slot slot = Slot.builder()
//                            .branch(branch)
//                            .staff(staff)
//                            .slotDate(date)
//                            .startTime(currentTime)
//                            .endTime(currentTime.plusMinutes(slotDuration))
//                            .durationInMinutes(slotDuration)
//                            .available(true)
//                            .slotType(SlotType.WORKING)
//                            .build();
//
//                    slotRepo.save(slot);
//                    currentTime = currentTime.plusMinutes(slotDuration);
//                    count++;
//                }
//
//                result.append("✅ Generated ").append(count).append(" slots for staff ").append(staff.getName())
//                        .append(" (Duration: ").append(slotDuration).append(" mins)\n");
//            }
//        }
//
//        return result.toString();
//    }



    @Override
    public void deleteSlotsForDate(LocalDate date) {
        slotRepo.deleteBySlotDate(date);
    }

    @Override
    public void deleteAllSlots() {
        slotRepo.deleteAll();
    }

}
