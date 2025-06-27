package com.sasika.salon.booking.service.impl;

import com.sasika.salon.booking.entity.Staff;
import com.sasika.salon.booking.repository.StaffRepository;
import com.sasika.salon.booking.service.StaffService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class StaffServiceImpl implements StaffService {

    private static final Logger logger = Logger.getLogger(StaffServiceImpl.class.getName());

    private final StaffRepository staffRepository;
    public StaffServiceImpl(StaffRepository staffRepository){
        this.staffRepository = staffRepository;
    }

    @Override
    public List<Staff> getAllStaff() {
        logger.info("Getting all staff");
        return staffRepository.findAll();
    }

    @Override
    public Staff getStaffById(Long id) {
        logger.info("Fetching staff with ID: "+id);
        return staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff with ID: "+id+" not found"));
    }

    @Override
    public Staff createStaff(Staff staff) {
        logger.info("Creating staff with ID: "+staff.getId());
        return staffRepository.save(staff);
    }

    @Override
    public Staff updateStaff(Long id, Staff staff) {
        logger.info("Updating staff with ID: "+id);
        return staffRepository.findById(id)
                .map(existingStaff->{
                    existingStaff.setName(staff.getName());
                    existingStaff.setEmail(staff.getEmail());
                    existingStaff.setPhoneNumber(staff.getPhoneNumber());
                    existingStaff.setBranch(staff.getBranch());
                    existingStaff.setAppointments(staff.getAppointments());
                    return staffRepository.save(existingStaff);
                })
                .orElseThrow(()->{
                    logger.warning("Staff with ID: "+id+" not found");
                    throw new RuntimeException("Staff with ID: "+id+" not found");
                });
    }

    @Override
    public String deleteStaff(Long id) {
        logger.info("Deleting staff with ID: "+id);

        boolean exists = staffRepository.existsById(id);

        if(!exists){
            return "Staff with ID: "+id+" not found";
        }
        staffRepository.deleteById(id);
        return "Staff with ID: "+id+" successfully deleted";
    }
}
