package com.sasika.salon.booking.repository;

import com.sasika.salon.booking.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long> {
}
