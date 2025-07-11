package com.sasika.salon.booking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private Long id;

    @NotBlank(message = "Customer name is required")
    private String name;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^(0\\d{9})$",
            message = "Phone number must start with 0 and be 10 digits long"
    )
    private String phoneNumber;


    @Email(message = "Invalid email format")
    private String email;

    // Avoid exposing password in DTO. Include it only if required for creation/registration
    private String password;
}
