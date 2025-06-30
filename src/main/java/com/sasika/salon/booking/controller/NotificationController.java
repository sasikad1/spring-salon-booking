package com.sasika.salon.booking.controller;

import com.sasika.salon.booking.dto.NotificationDto;
import com.sasika.salon.booking.entity.Notification;
import com.sasika.salon.booking.service.AppointmentService;
import com.sasika.salon.booking.service.CustomerService;
import com.sasika.salon.booking.service.NotificationService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    private final NotificationService notificationService;
    private final CustomerService customerService;
    private final AppointmentService appointmentService;
    private final ModelMapper modelMapper;

    public NotificationController(
            NotificationService notificationService,
            CustomerService customerService,
            AppointmentService appointmentService,
            ModelMapper modelMapper
    ) {
        this.notificationService = notificationService;
        this.customerService = customerService;
        this.appointmentService = appointmentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<NotificationDto>> getAllNotifications() {
        List<NotificationDto> notifications = notificationService.getAllNotifications()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDto> getNotificationById(@PathVariable Long id) {
        Notification notification = notificationService.getNotificationById(id);
        return ResponseEntity.ok(convertToDTO(notification));
    }

    @PostMapping
    public ResponseEntity<NotificationDto> createNotification(@Valid @RequestBody NotificationDto dto) {
        Notification notification = convertToEntity(dto);
        Notification created = notificationService.createNotification(notification);
        return new ResponseEntity<>(convertToDTO(created), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationDto> updateNotification(@PathVariable Long id, @Valid @RequestBody NotificationDto dto
    ) {
        Notification entity = convertToEntity(dto);
        entity.setId(id); // Set the ID from path to the entity
        Notification updated = notificationService.updateNotification(entity);
        return ResponseEntity.ok(convertToDTO(updated));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable Long id) {
        String result = notificationService.deleteNotification(id);
        if (result.contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.ok(result);
    }

    // Convert Entity -> DTO
    private NotificationDto convertToDTO(Notification notification) {
        NotificationDto dto = modelMapper.map(notification, NotificationDto.class);
        dto.setCustomerId(notification.getCustomer().getId());
        dto.setAppointmentId(notification.getAppointment().getId());
        return dto;
    }

    // Convert DTO -> Entity
// Helper method to convert DTO to Entity
    private Notification convertToEntity(NotificationDto dto) {
        if (dto.getCustomerId() == null) {
            throw new IllegalArgumentException("customerId cannot be null");
        }
        if (dto.getAppointmentId() == null) {
            throw new IllegalArgumentException("appointmentId cannot be null");
        }
        Notification entity = modelMapper.map(dto, Notification.class);
        entity.setCustomer(customerService.getCustomer(dto.getCustomerId()));
        entity.setAppointment(appointmentService.getAppointmentById(dto.getAppointmentId()));
        return entity;
    }
}
