package com.example.DocLib.dto;

import com.example.DocLib.dto.doctor.DoctorDto;
import com.example.DocLib.dto.patient.PatientDto;
import com.example.DocLib.enums.Gender;
import com.example.DocLib.enums.Roles;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotBlank(message = "Name is required")
    private String name;


    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Size(min = 10, message = "Phone Number must be at least 10 characters long")
    @Pattern(regexp = "^\\+?[0-9]{10,14}$", message = "Phone number must be between 10 and 15 characters and may start with +")
    private String phoneNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate DOB;

    private String ImageUrl;

    @NotNull(message = "Role is required")
    private Roles role;

    @NotNull(message = "Gender is required")
    private Gender gender;

    private LocalDate createdAt;
    @JsonProperty("patient")
    private PatientDto patient;
    @JsonProperty("doctor")
    private DoctorDto doctor;
}
