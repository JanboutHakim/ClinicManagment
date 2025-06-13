package com.example.DocLib.dto.doctor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeBlock {
    @NotNull
    private LocalTime start;

    @NotNull
    private LocalTime end;

}
