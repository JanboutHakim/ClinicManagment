
package com.example.DocLib.dto.patient;

import com.example.DocLib.dto.AppointmentDto;
import com.example.DocLib.dto.InsuranceCompanyDto;
import com.example.DocLib.dto.UserDto;
import com.example.DocLib.models.patient.Measurement;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientDto extends UserDto {

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    private String address;

    private List<AnalyseDto> analyses;

    private List<PatientHistoryRecordDto> patientHistoryRecords;

    private List<PatientDrugDto> drugs;

    private List<AppointmentDto> appointments;

    private List<InsuranceCompanyDto> insuranceCompanies;

    private List<Measurement> measurements;

}