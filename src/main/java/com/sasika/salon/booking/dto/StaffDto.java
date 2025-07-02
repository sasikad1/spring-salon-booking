package com.sasika.salon.booking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffDto {
    private Long id;

    @NotBlank(message = "Staff name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^(0\\d{9})$",
            message = "Phone number must start with 0 and be 10 digits long"
    )
    private String phoneNumber;

    private Integer slotDurationInMinutes;

    private String role; // e.g., Hairdresser, Barber, etc.

    @NotNull(message = "Branch ID is required")
    private Long branchId;
}
