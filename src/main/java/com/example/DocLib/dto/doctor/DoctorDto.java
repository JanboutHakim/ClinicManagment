package com.example.DocLib.dto.doctor;

import com.example.DocLib.dto.appointment.AppointmentDto;
import com.example.DocLib.dto.InsuranceCompanyDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DoctorDto {

    private Long id;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Clinic Name  is required")
    private String clinicName;

    @Min(value = 1, message = "Checkup duration must be positive")
    private int checkupDurationInMinutes;

    private double checkupPrice;

    private List<SpecializationsDto> specialization;

    @Min(value = 0, message = "Years of experience cannot be negative")
    private int yearsOfExperience;

    @NotBlank(message = "Union membership number is required")
    private String unionMembershipNumber;

    private List<AppointmentDto> appointments;

    private List<ExperienceDto> experiences;

    private List<ScientificBackgroundDto> scientificBackgrounds;

    private List<DoctorServiceDto> services;

    private List<DoctorScheduleDto> doctorSchedules;

    private List<DoctorHolidayScheduleDto> holidaySchedules;

    private List<InsuranceCompanyDto> insuranceCompanies;
}