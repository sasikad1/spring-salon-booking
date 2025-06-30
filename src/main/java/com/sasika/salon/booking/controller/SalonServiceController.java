package com.sasika.salon.booking.controller;

import com.sasika.salon.booking.entity.SalonService;
import com.sasika.salon.booking.service.SalonServiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salon-services")
@CrossOrigin(origins = "*")
public class SalonServiceController {

    private final SalonServiceService salonServiceService;

    public SalonServiceController(SalonServiceService salonServiceService) {
        this.salonServiceService = salonServiceService;
    }

    @GetMapping
    public ResponseEntity<List<SalonService>> getAllServices() {
        List<SalonService> services = salonServiceService.getAllService();
        return ResponseEntity.ok(services);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalonService> getServiceById(@PathVariable Long id) {
        SalonService service = salonServiceService.findServiceById(id);
        return ResponseEntity.ok(service);
    }

    @PostMapping
    public ResponseEntity<SalonService> createService(@Valid @RequestBody SalonService salonService) {
        SalonService createdService = salonServiceService.createService(salonService);
        return new ResponseEntity<>(createdService, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalonService> updateService(@PathVariable Long id, @Valid @RequestBody SalonService salonService) {
        SalonService updatedService = salonServiceService.updateServiceById(id, salonService);
        return ResponseEntity.ok(updatedService);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteService(@PathVariable Long id) {
        String result = salonServiceService.deleteServiceById(id);
        if (result.contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.ok(result);
    }
}
