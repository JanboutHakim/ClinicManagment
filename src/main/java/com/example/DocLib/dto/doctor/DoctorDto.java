package com.example.DocLib.dto.doctor;

import com.example.DocLib.dto.appointment.AppointmentDto;
import com.example.DocLib.dto.InsuranceCompanyDto;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Clinic Phone Number is required")
    @Size(min = 10, message = "Phone Number must be at least 10 characters long")
    @Pattern(regexp = "^\\+?[0-9]{10,14}$", message = "Phone number must be between 10 and 15 characters and may start with +")
    private String clinicPhoneNumber;

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