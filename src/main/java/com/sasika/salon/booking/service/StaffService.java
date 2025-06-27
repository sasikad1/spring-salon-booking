package com.sasika.salon.booking.service;

import com.sasika.salon.booking.entity.Staff;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StaffService {
    List<Staff> getAllStaff();
    Staff getStaffById(Long id);
    Staff createStaff(Staff staff);
    Staff updateStaff(Long id, Staff staff);
    String deleteStaff(Long id);
}
