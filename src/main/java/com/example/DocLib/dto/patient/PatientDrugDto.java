package com.example.DocLib.dto.patient;

import com.example.DocLib.enums.DrugStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDrugDto {
    @NotNull
    private Long id;

    @NotNull
    private Long patientId;

    @NotNull
    private Long drugId;

    @NotBlank
    @Size(max = 256)
    private String name;

    @NotBlank
    @Size(max = 256)
    private String scientificName;

    @NotBlank
    @Size(max = 256)
    private String medicationDosage;

    @NotBlank
    @Size(max = 256)
    private String pharmaceuticalForm;

    @NotBlank
    @Size(max = 256)
    private String company;

    private boolean isNew;

    @NotNull
    private DrugStatus drugStatus;

    private List<DrugAlarmDto> drugAlarms;

}