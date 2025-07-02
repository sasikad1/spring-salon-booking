package com.sasika.salon.booking.dto;

import lombok.*;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkingHoursDto {
    private String dayOfWeek; // e.g., "MONDAY", "SUNDAY"
    private String openTime;  // "08:00"
    private String closeTime; // "17:00"
    private boolean closed;
}
