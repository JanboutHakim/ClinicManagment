
package com.example.DocLib.dto.patient;

import com.example.DocLib.dto.appointment.AppointmentDto;
import com.example.DocLib.dto.InsuranceCompanyDto;
import com.example.DocLib.models.patient.Measurement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientDto   {


    private Long patientId;

    private String address;

    private List<AnalyseDto> analyses;

    private List<PatientHistoryRecordDto> patientHistoryRecords;

    private List<PatientDrugDto> drugs;

    private List<AppointmentDto> appointments;

    private List<InsuranceCompanyDto> insuranceCompanies;

    private List<MeasurementDto> measurements;

}