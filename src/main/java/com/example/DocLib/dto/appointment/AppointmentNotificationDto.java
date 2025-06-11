package com.example.DocLib.dto.appointment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentNotificationDto {
    private String content;
    private AppointmentDto appointmentDto;


}
