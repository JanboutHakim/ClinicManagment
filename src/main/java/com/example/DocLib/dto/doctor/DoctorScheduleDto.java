package com.example.DocLib.dto.doctor;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;
@Getter
@Setter
@Data
public class DoctorScheduleDto {


        private Long doctorId;
        private Long id;

        @NotNull(message = "Day of the Week cannot be null")
        private DayOfWeek dayOfWeek;

        @NotNull(message = "Start Time cannot be null")
        private LocalTime startTime;

        @NotNull(message = "End Time cannot be null")
        private LocalTime endTime;



}
