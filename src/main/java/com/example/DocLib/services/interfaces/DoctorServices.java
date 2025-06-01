package com.example.DocLib.services.interfaces;

import com.example.DocLib.dto.*;
import com.example.DocLib.dto.doctor.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DoctorServices {

        // Core Doctor Management
        DoctorDto getDoctorById(Long id);
        List<DoctorDto> getAllDoctors();

        // Basic Field Updates
        DoctorDto updateAddress(Long id, String address);

        DoctorDto updateYearsOfExperience(Long id, int years);
        DoctorDto updateUnionMembershipNumber(Long id, String number);

        //Specializations
        List<SpecializationsDto> getSpecializations(Long doctorId);
        DoctorDto addSpecialization(Long id, SpecializationsDto specializationsDto);
        DoctorDto removeSpecialization(Long id, Long specializationId);
        DoctorDto updateSpecialization(Long id, SpecializationsDto specializationsDto);



        // Experiences
        List<ExperienceDto> getExperiences(Long doctorId);
        DoctorDto addExperience(Long doctorId, ExperienceDto experienceDto);
        DoctorDto removeExperience(Long doctorId, Long experienceId);
        DoctorDto updateExperience(Long doctorId, ExperienceDto experienceDto);

        // Scientific Background
        List<ScientificBackgroundDto> getScientificBackgrounds(Long doctorId);
        DoctorDto addScientificBackground(Long doctorId, ScientificBackgroundDto scientificBackgroundDto);
        DoctorDto removeScientificBackground(Long doctorId, Long scientificBackgroundId);

        // Services Offered
        List<DoctorServiceDto> getServices(Long doctorId);

        @Transactional
        DoctorDto updateScientificBackground(Long doctorId, ScientificBackgroundDto dto);

        DoctorDto addService(Long doctorId, DoctorServiceDto doctorServiceDto);
        DoctorDto removeService(Long doctorId, Long serviceId);
        DoctorDto updateService(Long doctorId, DoctorServiceDto doctorServiceDto);

        // Schedule (Patients here seem like schedules)
        List<DoctorScheduleDto> getSchedules(Long doctorId);
        DoctorDto addSchedule(Long doctorId, DoctorScheduleDto scheduleDto);
        DoctorDto removeSchedule(Long doctorId, Long doctorScheduleId);

        // Holiday Schedule
        List<DoctorHolidayScheduleDto> getHolidaySchedules(Long doctorId);

        @Transactional
        DoctorDto updateSchedule(Long doctorId, DoctorScheduleDto dto);

        DoctorDto addHolidaySchedule(Long doctorId, DoctorHolidayScheduleDto doctorHolidayScheduleDto);
        DoctorDto removeHolidaySchedule(Long doctorId, Long doctorHolidayScheduleId);

        // Insurance Companies
        List<InsuranceCompanyDto> getInsuranceCompanies(Long doctorId);

        @Transactional
        DoctorDto updateHolidaySchedule(Long doctorId, DoctorHolidayScheduleDto dto);

        DoctorDto addInsuranceCompany(Long doctorId, InsuranceCompanyDto insuranceCompanyDto);
        DoctorDto removeInsuranceCompany(Long doctorId, Long insuranceCompanyId);


}
