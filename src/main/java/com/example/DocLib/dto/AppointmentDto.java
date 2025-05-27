package com.example.DocLib.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {
    private Long doctorId;
    private Long patientId;
    private Long id;
    private Date appointmentDate;
    private Time startTime;
    private Time endTime;
    private String note;
}