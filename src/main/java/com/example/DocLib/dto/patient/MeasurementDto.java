package com.example.DocLib.dto.patient;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementDto {
    @NotNull
    private Long id;
    @NotNull
    @PastOrPresent
    private LocalDate date;

    @NotNull
    @DecimalMin(value = "0.0", message = "Sugar level must be positive")
    @DecimalMax(value = "999.9", message = "Sugar level is too high")
    private Double sugarLevel;

    @NotNull
    @Pattern(regexp = "^\\d{2,3}/\\d{2,3}$", message = "Blood pressure must be in the 'systolic/diastolic' format (ex '120/80')")
    private String bloodPressure;
}