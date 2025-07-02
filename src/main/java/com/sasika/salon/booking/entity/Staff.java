package com.sasika.salon.booking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Staff {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(
            regexp = "^(0\\d{9})$",
            message = "Phone number must start with 0 and be 10 digits long"
    )
    private String phoneNumber;

    @Min(value = 1, message = "Slot duration must be at least 1 minute")
    private Integer slotDurationInMinutes; // slot length in minutes

    private String role; // e.g., Hairdresser, Barber, etc.

    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL)
    private List<Slot> slots;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL)
    private List<Appointment> appointments;

}
