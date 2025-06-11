package com.example.DocLib.dto.doctor;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IntegerDto {
    @NotNull
    private int number;
}
