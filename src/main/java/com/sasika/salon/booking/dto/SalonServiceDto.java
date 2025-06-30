package com.sasika.salon.booking.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalonServiceDto {

    private Long id;

    @NotBlank(message = "Service name is required")
    private String name;

    private String description;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Integer duration_minutes;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;
}
