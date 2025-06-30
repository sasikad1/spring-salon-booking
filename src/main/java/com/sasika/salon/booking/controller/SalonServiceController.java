package com.sasika.salon.booking.controller;

import com.sasika.salon.booking.dto.SalonServiceDto;
import com.sasika.salon.booking.entity.SalonService;
import com.sasika.salon.booking.service.SalonServiceService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/salon-services")
@CrossOrigin(origins = "*")
public class SalonServiceController {

    private final SalonServiceService salonServiceService;
    private final ModelMapper modelMapper;

    public SalonServiceController(SalonServiceService salonServiceService, ModelMapper modelMapper) {
        this.salonServiceService = salonServiceService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<SalonServiceDto>> getAllServices() {
        List<SalonServiceDto> services = salonServiceService.getAllService()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(services);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalonServiceDto> getServiceById(@PathVariable Long id) {
        SalonService service = salonServiceService.findServiceById(id);
        return ResponseEntity.ok(convertToDTO(service));
    }

    @PostMapping
    public ResponseEntity<SalonServiceDto> createService(@Valid @RequestBody SalonServiceDto dto) {
        SalonService created = salonServiceService.createService(convertToEntity(dto));
        return new ResponseEntity<>(convertToDTO(created), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalonServiceDto> updateService(@PathVariable Long id, @Valid @RequestBody SalonServiceDto dto) {
        SalonService updated = salonServiceService.updateServiceById(id, convertToEntity(dto));
        return ResponseEntity.ok(convertToDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteService(@PathVariable Long id) {
        String result = salonServiceService.deleteServiceById(id);
        if (result.contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.ok(result);
    }

    // DTO -> Entity
    private SalonService convertToEntity(SalonServiceDto dto) {
        return modelMapper.map(dto, SalonService.class);
    }

    // Entity -> DTO
    private SalonServiceDto convertToDTO(SalonService entity) {
        return modelMapper.map(entity, SalonServiceDto.class);
    }
}
