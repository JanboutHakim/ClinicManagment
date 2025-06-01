package com.example.DocLib.dto.doctor;

import com.example.DocLib.dto.appointment.AppointmentDto;
import com.example.DocLib.dto.InsuranceCompanyDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DoctorDto {

    private Long id;

    private String address;

    private int checkupDuration;

    private List<SpecializationsDto> specialization;

    private int yearsOfExperience;

    private String unionMembershipNumber;

    private List<AppointmentDto> appointments;

    private List<ExperienceDto> experiences;

    private List<ScientificBackgroundDto> scientificBackgrounds;

    private List<DoctorServiceDto> services;

    private List<DoctorScheduleDto> doctorSchedules;

    private List<DoctorHolidayScheduleDto> holidaySchedules;

    private List<InsuranceCompanyDto> insuranceCompanies;
}