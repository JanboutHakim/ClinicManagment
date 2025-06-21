package com.example.DocLib.dto.patient;

import com.example.DocLib.enums.DrugStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDrugDto {
    private Long id;

    private Long patientId;

    private Long drugId;

    @NotNull
    private int frequency;

    private String drugName;

    @Size(max = 256)
    private String dosage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate ;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate ;

    private DrugStatus drugStatus;

    private List<DrugAlarmDto> drugAlarms;

}