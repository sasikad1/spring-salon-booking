package com.sasika.salon.booking.entity;

import com.sasika.salon.booking.enums.SlotType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private LocalTime startTime;
    private LocalTime endTime;

    private boolean isAvailable = true; // false if manually blocked/unavailable

    private String reason; // e.g., "Lunch break", "Sick leave", "Branch holiday"

    @Enumerated(EnumType.STRING)
    private SlotType slotType; // WORKING, BREAK, BLOCKED, CUSTOM_UNAVAILABLE

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @OneToOne(mappedBy = "slot", cascade = CascadeType.ALL)
    private Appointment appointment;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
