package com.sasika.salon.booking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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

    private String firstName;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(
            regexp = "^(0\\d{9})$",
            message = "Phone number must start with 0 and be 10 digits long"
    )
    private String phoneNumber;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL)
    private List<Appointment> appointments;

}
