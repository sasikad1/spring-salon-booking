package com.sasika.salon.booking.repository;

import com.sasika.salon.booking.entity.SalonService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface SalonServiceRepository extends JpaRepository<SalonService, Long> {
}
