package com.sasika.salon.booking.controller;

import com.sasika.salon.booking.dto.AppointmentDto;
import com.sasika.salon.booking.entity.*;
import com.sasika.salon.booking.service.*;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final CustomerService customerService;
    private final StaffService staffService;
    private final BranchService branchService;
    private final SalonServiceService salonServiceService;
    private final ModelMapper modelMapper;

    public AppointmentController(AppointmentService appointmentService,
                                 CustomerService customerService,
                                 StaffService staffService,
                                 BranchService branchService,
                                 SalonServiceService salonServiceService,
                                 ModelMapper modelMapper) {
        this.appointmentService = appointmentService;
        this.customerService = customerService;
        this.staffService = staffService;
        this.branchService = branchService;
        this.salonServiceService = salonServiceService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDto>> getAllAppointments() {
        List<AppointmentDto> appointments = appointmentService.getAllAppointments()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(convertToDTO(appointment));
    }

    @PostMapping
    public ResponseEntity<AppointmentDto> createAppointment(@Valid @RequestBody AppointmentDto dto) {
        Appointment created = appointmentService.createAppointment(convertToEntity(dto));
        return new ResponseEntity<>(convertToDTO(created), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDto> updateAppointment(@PathVariable Long id, @Valid @RequestBody AppointmentDto dto) {
        Appointment updated = appointmentService.updateAppointmentById(id, convertToEntity(dto));
        return ResponseEntity.ok(convertToDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id) {
        String result = appointmentService.deleteAppointmentById(id);
        if (result.contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.ok(result);
    }

    // ------- Helper Methods ---

    private Appointment convertToEntity(AppointmentDto dto) {
        Appointment appointment = modelMapper.map(dto, Appointment.class);

        appointment.setCustomer(customerService.getCustomer(dto.getCustomerId()));
        appointment.setStaff(staffService.getStaffById(dto.getStaffId()));
        appointment.setBranch(branchService.getBranchById(dto.getBranchId()));

        if (dto.getSalonServiceIds() != null) {
            List<SalonService> services = dto.getSalonServiceIds().stream()
                    .map(salonServiceService::findServiceById)
                    .collect(Collectors.toList());
            appointment.setSalonServices(services);
        }

        return appointment;
    }

    private AppointmentDto convertToDTO(Appointment appointment) {
        AppointmentDto dto = modelMapper.map(appointment, AppointmentDto.class);

        dto.setCustomerId(appointment.getCustomer().getId());
        dto.setStaffId(appointment.getStaff().getId());
        dto.setBranchId(appointment.getBranch().getId());

        if (appointment.getSalonServices() != null) {
            List<Long> serviceIds = appointment.getSalonServices()
                    .stream()
                    .map(SalonService::getId)
                    .collect(Collectors.toList());
            dto.setSalonServiceIds(serviceIds);
        }

        return dto;
    }
}
