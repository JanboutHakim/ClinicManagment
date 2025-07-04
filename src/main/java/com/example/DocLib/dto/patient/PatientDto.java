
package com.example.DocLib.dto.patient;

import com.example.DocLib.dto.appointment.AppointmentDto;
import com.example.DocLib.dto.InsuranceCompanyDto;
import com.example.DocLib.enums.Gender;
import com.example.DocLib.enums.Roles;
import com.example.DocLib.models.patient.Measurement;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
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

    @NotBlank(message = "Name is required")
    private String name;



    @NotBlank(message = "Phone number is required")
    @Size(min = 10, message = "Phone Number must be at least 10 characters long")
    @Pattern(regexp = "^\\+?[0-9]{10,14}$", message = "Phone number must be between 10 and 15 characters and may start with +")
    private String phoneNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate DOB;

    private String ImageUrl;


    @NotNull(message = "Gender is required")
    private Gender gender;


    private List<AnalyseDto> analyses;

    private List<PatientHistoryRecordDto> patientHistoryRecords;

    private List<PatientDrugDto> drugs;

    private List<AppointmentDto> appointments;

    private List<InsuranceCompanyDto> insuranceCompanies;

    private List<MeasurementDto> measurements;

}