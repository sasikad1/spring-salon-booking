package com.sasika.salon.booking.service.impl;

import com.sasika.salon.booking.entity.SalonService;
import com.sasika.salon.booking.repository.SalonServiceRepository;
import com.sasika.salon.booking.service.SalonServiceService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class SalonServiceServiceImpl implements SalonServiceService {

    private static final Logger logger = LoggerFactory.getLogger(SalonServiceServiceImpl.class);

    private final SalonServiceRepository salonServiceRepository;

    public SalonServiceServiceImpl(SalonServiceRepository salonServiceRepository) {
        this.salonServiceRepository = salonServiceRepository;
    }

    @Override
    public List<SalonService> getAllService() {
        logger.info("Fetching all salon services");
        return salonServiceRepository.findAll();
    }

    @Override
    public SalonService findServiceById(Long id) {
        logger.info("Fetching SalonService with ID: " + id);
        return salonServiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SalonService with ID: " + id + " not found"));
    }

    @Override
    public SalonService createService(SalonService salonService) {
        logger.info("Creating new SalonService: {}", salonService);
        return salonServiceRepository.save(salonService);
    }

    @Override
    public SalonService updateServiceById(Long id, SalonService salonService) {
        logger.info("Updating SalonService with ID: {}", id);
        return salonServiceRepository.findById(id)
                .map(existing -> {
                    existing.setName(salonService.getName());
                    existing.setDescription(salonService.getDescription()); // newly added line
                    existing.setDuration_minutes(salonService.getDuration_minutes());
                    existing.setPrice(salonService.getPrice());
                    return salonServiceRepository.save(existing);
                })
                .orElseThrow(() -> {
                    logger.warn("SalonService with ID {} not found", id);
                    return new RuntimeException("SalonService not found with ID: " + id);
                });
    }

    @Override
    public String deleteServiceById(Long id) {
        logger.info("Deleting SalonService with ID: {}", id);
        if (!salonServiceRepository.existsById(id)) {
            return "SalonService with ID: " + id + " not found";
        }
        salonServiceRepository.deleteById(id);
        return "SalonService with ID " + id + " deleted successfully";
    }
}
