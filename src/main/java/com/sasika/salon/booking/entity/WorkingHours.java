package com.sasika.salon.booking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkingHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dayOfWeek; // e.g., "MONDAY", "SUNDAY"

    private LocalTime openTime; //branch open time

    private LocalTime closeTime; //branch close time

    private boolean closed;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
}
