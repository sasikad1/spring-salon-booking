package com.sasika.salon.booking.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentDTO {

    private Long id;

    private Long customerId;

    private Long staffId;

    private Long branchId;

    private LocalDate appointmentDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private String status;

    private List<Long> salonServiceIds;

    private List<Long> notificationIds;
}
