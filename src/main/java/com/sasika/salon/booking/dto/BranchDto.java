package com.sasika.salon.booking.dto;

import com.sasika.salon.booking.entity.WorkingHours;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchDto {
    private Long id;

    @NotBlank(message = "Branch name is required")
    private String name;

    @NotBlank(message = "Branch address is required")
    private String address;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^(0\\d{9})$",
            message = "Phone number must start with 0 and 9 digits long"
    )
    private String phoneNumber;

    @NotBlank(message = "Email is required")
    private String email;

    private List<WorkingHoursDto> workingHours;
}
