package com.example.DocLib.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponseDto {
    private Long id;
    private String name;
    private String phoneNumber;
    private String address;
}
