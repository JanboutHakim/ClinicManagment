package com.example.DocLib.dto.appointment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentNotificationDto {
    private String content;

    public AppointmentNotificationDto() {}
    public AppointmentNotificationDto(String content) {
        this.content = content;
    }

    // Getters & setters
}
