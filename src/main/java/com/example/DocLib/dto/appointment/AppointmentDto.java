package com.example.DocLib.dto.appointment;

import com.example.DocLib.enums.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {
    private Long id;
    @NotNull
    private Long patientId;
    @NotNull
    private Long doctorId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String reason;
    private String notes;
    @Null
    private AppointmentStatus status;
    private String cancellationReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}