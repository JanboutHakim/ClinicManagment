package com.example.DocLib.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

@Entity
@Table(name = "holiday_schedule")
public class HolidaySchedule {
    @Id
    @ManyToOne
    @JoinColumn(name="doctor_id", nullable=false)
    private Doctor doctor;

    @Column(name = "holiday_date")
    private Date holidayDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private boolean allDay;
}
