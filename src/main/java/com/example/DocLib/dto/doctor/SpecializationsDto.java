package com.example.DocLib.dto.doctor;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Data
public class SpecializationsDto {
    private Long id;

    private Long doctorId;

    @NotBlank(message = "Specialization is required.")
    @Size(max = 250, message = "Specialization should be maximum 250 characters.")
    private String specialization;
}
