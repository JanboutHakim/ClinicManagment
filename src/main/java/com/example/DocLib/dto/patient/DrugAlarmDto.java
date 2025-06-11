package com.example.DocLib.dto.patient;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DrugAlarmDto {
    @NotNull
    private Long patientDrugId;

    @Size(max=500,message = "The Message to Tall")
    private String alarmMessage;
    @NotNull
    @FutureOrPresent
    private LocalDateTime alarmTime;
}