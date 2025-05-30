package com.example.DocLib.dto.doctor;

import java.sql.Date;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class ExperienceDto {
    private Long Id;
    private Long doctorId;

    @NotBlank(message = "Experience description is required.")
    @Size(max = 255, message = "Experience description should not be greater than 255 characters.")
    private String experienceDescription;

    @Size(max = 255, message = "Experience description should not be greater than 255 characters.")
    private String position;

    @Past(message = "Start date should be in the past.")
    @NotNull(message = "Start date is required.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @PastOrPresent(message = "End date should be in the past or present.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}
