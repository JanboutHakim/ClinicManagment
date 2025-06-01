package com.example.DocLib.dto.doctor;

import com.example.DocLib.enums.Services;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DoctorServiceDto {


    private Long doctorId;
    private Long id;
    @NotNull(message = "The Service can't be Empty")
    private Services services;
}
