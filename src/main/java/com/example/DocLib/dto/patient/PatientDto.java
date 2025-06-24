
package com.example.DocLib.dto.patient;

import com.example.DocLib.dto.appointment.AppointmentDto;
import com.example.DocLib.dto.InsuranceCompanyDto;
import com.example.DocLib.models.patient.Measurement;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientDto   {


    private Long patientId;

    @NotBlank(message = "Address is required")
    private String address;

    @Pattern(regexp = "\\[A,B,AB,O][+,-]",message = "The Blood Shape is Wrong")
    private String bloodType;

    @Size(min = 1,max = 180)
    private double weight;



    private List<AnalyseDto> analyses;

    private List<PatientHistoryRecordDto> patientHistoryRecords;

    private List<PatientDrugDto> drugs;

    private List<AppointmentDto> appointments;

    private List<InsuranceCompanyDto> insuranceCompanies;

    private List<MeasurementDto> measurements;

}