package com.sasika.salon.booking.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDto {

    private Long id;

    private Long customerId;

    private Long appointmentId;

    private String type;

    private LocalDateTime sentAt;
}
