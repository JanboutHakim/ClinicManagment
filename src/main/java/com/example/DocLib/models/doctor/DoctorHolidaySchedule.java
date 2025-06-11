package com.example.DocLib.models.doctor;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

@Entity
@Table(name = "holiday_schedule")
public class DoctorHolidaySchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="doctor_id", nullable=false)
    private Doctor doctor;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private boolean allDay;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoctorHolidaySchedule)) return false;
        DoctorHolidaySchedule that = (DoctorHolidaySchedule) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
