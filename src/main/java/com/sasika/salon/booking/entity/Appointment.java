package com.sasika.salon.booking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @NotNull
    @Column(nullable = false)
    private LocalDate appointmentDate;

    @NotNull
    @Column(nullable = false)
    private LocalTime startTime;

    @NotNull
    @Column(nullable = false)
    private LocalTime endTime;

    // Status (e.g., pending, confirmed, cancelled)
    private String status;

    @ManyToMany
    @JoinTable(
            name = "appointment_service",
            joinColumns = @JoinColumn(name = "appointment_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<Service> services;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL)
    private List<Notification> notifications;

}
