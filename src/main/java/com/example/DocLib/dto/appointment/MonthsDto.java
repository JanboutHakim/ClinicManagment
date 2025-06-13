package com.example.DocLib.dto.appointment;

import lombok.Data;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Data
public class MonthsDto {
    @Min(value = 1, message = "Months must be at least 1")
    @Max(value = 12, message = "Months must be at most 12")
    private int months;
}
