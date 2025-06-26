package com.sasika.salon.booking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Customer name is required")
    private String name;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^(0\\d{9})$", message = "Phone number must be 10 digits starting with 0")
    private String phoneNumber;

    @Email(message = "Invalid email format")
    private String email;

    private String password;

    @OneToMany(mappedBy ="customer",  cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "customer",  cascade = CascadeType.ALL)
    private List<Notification> notifications;
}
