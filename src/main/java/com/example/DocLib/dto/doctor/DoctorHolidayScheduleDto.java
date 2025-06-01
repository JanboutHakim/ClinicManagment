package com.example.DocLib.dto.doctor;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
public class DoctorHolidayScheduleDto {
    private Long id;

    private long doctorId;

    @PastOrPresent(message = "Holiday Date should be in the past or present.")
    @NotNull(message = "Holiday Date is required.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate holidayDate;

    @FutureOrPresent
    private LocalTime startTime;

    @Future
    private LocalTime endTime;

    private boolean allDay;

}
