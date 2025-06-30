package com.sasika.salon.booking.service;

import com.sasika.salon.booking.entity.SalonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SalonServiceService {
    List<SalonService> getAllService();
    SalonService findServiceById(Long id);
    SalonService createService(SalonService salonService);
    SalonService updateServiceById(Long id, SalonService salonService);
    String deleteServiceById(Long id);
}
