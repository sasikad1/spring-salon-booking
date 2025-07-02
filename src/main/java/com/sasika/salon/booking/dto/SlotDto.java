package com.sasika.salon.booking.dto;

import com.sasika.salon.booking.enums.SlotType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlotDto {

    private Long id;

    private Long branchId;

    private Long staffId;

    private LocalDate slotDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private boolean available;

    private SlotType slotType;

    private Integer durationInMinutes;
}
