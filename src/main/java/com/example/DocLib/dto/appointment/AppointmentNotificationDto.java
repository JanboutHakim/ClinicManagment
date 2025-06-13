package com.example.DocLib.dto.appointment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentNotificationDto {
    @NotBlank(message = "Content is required")
    @Size(max = 255, message = "Content must be at most 255 characters")
    private String content;

    @NotNull(message = "Appointment is required")
    private AppointmentDto appointmentDto;


}
