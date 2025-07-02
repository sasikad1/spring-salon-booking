package com.sasika.salon.booking.entity;

import com.sasika.salon.booking.enums.SlotType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔗 Slot belongs to a Branch
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    // 🔗 Slot belongs to a Staff (who defines slot duration)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    // 📅 Date this slot is valid for (e.g., 2025-07-01)
    @Column(nullable = false)
    private LocalDate slotDate;

    // 🕒 Slot start and end time
    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    // ✅ Whether the slot is available for booking
    @Builder.Default
    @Column(nullable = false)
    private boolean available = true;

    // 🔄 Slot type (default = WORKING)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SlotType slotType = SlotType.WORKING;

    // Optionally, store the actual slot duration (snapshot)
    @Column(nullable = false)
    private Integer durationInMinutes;
}
