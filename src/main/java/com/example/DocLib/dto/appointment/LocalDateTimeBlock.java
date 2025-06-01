package com.example.DocLib.dto.appointment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocalDateTimeBlock {
    private LocalDateTime start;
    private LocalDateTime end;
}
