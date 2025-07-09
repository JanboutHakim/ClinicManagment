package com.example.DocLib.services.implementation;

import com.example.DocLib.dto.InsuranceCompanyDto;
import com.example.DocLib.exceptions.custom.ResourceNotFoundException;
import com.example.DocLib.models.InsuranceCompany;
import com.example.DocLib.models.doctor.*;
import com.example.DocLib.repositories.DoctorRepository;
import com.example.DocLib.services.interfaces.DoctorServices;
import com.example.DocLib.dto.doctor.*;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServicesImp implements DoctorServices {

    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public DoctorServicesImp(DoctorRepository doctorRepository, ModelMapper modelMapper) {
        this.doctorRepository = doctorRepository;
        this.modelMapper = modelMapper;
    }

    // Basic doctor operations
    @Override
    public DoctorDto getDoctorById(Long id) {
        return convertDoctorToDto(getDoctorEntity(id));
    }

    @Override
    public List<DoctorDto> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(this::convertDoctorToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorDto> searchDoctors(String query) {
        return doctorRepository.searchDoctor(query).stream()
                .map(this::convertDoctorToDto)
                .toList();
    }

    @Override
    @Transactional
    public DoctorDto updateAddress(Long id, String address) {
        Doctor doctor = getDoctorEntity(id);
        doctor.setAddress(address);
        return convertDoctorToDto(doctor);
    }

    @Override
    @Transactional
    public DoctorDto updateClinicPhoneNumber(Long id, String clinicPhoneNumber) {
        Doctor doctor = getDoctorEntity(id);
        doctor.setClinicPhoneNumber(clinicPhoneNumber);
        return convertDoctorToDto(doctor);
    }

    private DoctorDto convertDoctorToDto(Doctor doctor) {
        return modelMapper.map(doctor, DoctorDto.class);
    }

    @Override
    @Transactional
    public DoctorDto updateCheckupDuration(Long id, IntegerDto integerDto) {
        Doctor doctor = getDoctorEntity(id);
        doctor.setCheckupDurationInMinutes(integerDto.getNumber());
        return convertDoctorToDto(doctor);
    }

    @Override
    @Transactional
    public DoctorDto updateYearsOfExperience(Long id, int years) {
        Doctor doctor = getDoctorEntity(id);
        doctor.setYearsOfExperience(years);
        return convertDoctorToDto(doctor);
    }

    @Override
    @Transactional
    public DoctorDto updateUnionMembershipNumber(Long id, String number) {
        Doctor doctor = getDoctorEntity(id);
        doctor.setUnionMembershipNumber(number);
        return convertDoctorToDto(doctor);
    }

    // Specialization operations
    @Override
    @Transactional
    public DoctorDto addSpecialization(Long doctorId, SpecializationsDto dto) {
        Doctor doctor = getDoctorEntity(doctorId);
        Specializations specialization = modelMapper.map(dto, Specializations.class);
        doctor.addSpecialization(specialization);
        return convertDoctorToDto(doctor);
    }

    @Override
    @Transactional
    public DoctorDto removeSpecialization(Long doctorId, Long specializationId) {
        Doctor doctor = getDoctorEntity(doctorId);
        doctor.removeSpecializationById(specializationId);
        return convertDoctorToDto(doctor);
    }

    @Override
    @Transactional
    public DoctorDto updateSpecialization(Long doctorId, SpecializationsDto dto) {
        Doctor doctor = getDoctorEntity(doctorId);

        doctor.updateSpecialization(dto.getId(), specialization -> {
            modelMapper.map(dto,specialization);
        });

        return convertDoctorToDto(doctor);
    }

    // Experience operations
    @Override
    @Transactional
    public DoctorDto addExperience(Long doctorId, ExperienceDto dto) {
        Doctor doctor = getDoctorEntity(doctorId);
        Experience experience = modelMapper.map(dto, Experience.class);
        doctor.addExperience(experience);
        return convertDoctorToDto(doctor);
    }

    @Override
    @Transactional
    public DoctorDto removeExperience(Long doctorId, Long experienceId) {
        Doctor doctor = getDoctorEntity(doctorId);

        doctor.removeExperienceById(experienceId);

        return convertDoctorToDto(doctor);
    }

    @Override
    @Transactional
    public DoctorDto updateExperience(Long doctorId, ExperienceDto dto) {
        Doctor doctor = getDoctorEntity(doctorId); // Fetch the managed Doctor entity

        doctor.updateExperience(dto.getId(), exp -> {modelMapper.map(dto,exp);});

        return convertDoctorToDto(doctor);
    }


    // Scientific Background operations
    @Override
    @Transactional
    public DoctorDto addScientificBackground(Long doctorId, ScientificBackgroundDto dto) {
        Doctor doctor = getDoctorEntity(doctorId);
        ScientificBackground background = modelMapper.map(dto, ScientificBackground.class);
        doctor.addScientificBackground(background);
        return convertDoctorToDto(doctor);
    }

    @Override
    @Transactional
    public DoctorDto removeScientificBackground(Long doctorId, Long backgroundId) {
        Doctor doctor = getDoctorEntity(doctorId);
        doctor.removeScientificBackgroundById(backgroundId);
        return convertDoctorToDto(doctor);
    }

    @Override
    @Transactional
    public DoctorDto updateScientificBackground(Long doctorId, ScientificBackgroundDto dto) {
        Doctor doctor = getDoctorEntity(doctorId);
        doctor.updateScientificBackground(dto.getId(), scientificBackground -> {
            modelMapper.map(dto,scientificBackground);
        });
        return convertDoctorToDto(doctor);
    }

    // Service operations
    @Override
    @Transactional
    public DoctorDto addService(Long doctorId, DoctorServiceDto dto) {
        Doctor doctor = getDoctorEntity(doctorId);
        System.out.println(dto.toString());
        DoctorService service = modelMapper.map(dto, DoctorService.class);
        doctor.addService(service);
        return convertDoctorToDto(doctor);
    }

    @Override
    @Transactional
    public DoctorDto removeService(Long doctorId, Long serviceId) {
        Doctor doctor = getDoctorEntity(doctorId);
        doctor.removeServiceById(serviceId);
        return convertDoctorToDto(doctor);
    }

    @Override
    @Transactional
    public DoctorDto updateService(Long doctorId, DoctorServiceDto dto) {
        Doctor doctor = getDoctorEntity(doctorId);
        doctor.updateExperience(dto.getId(), analyse -> {
            modelMapper.map(dto, analyse);
        });
        return convertDoctorToDto(doctor);
    }

    // Schedule operations
    @Override
    @Transactional
    @CacheEvict(value = "availableSlots", key = "#doctorId")
    public DoctorDto addSchedule(Long doctorId, DoctorScheduleDto dto) {
        Doctor doctor = getDoctorEntity(doctorId);
        DoctorSchedule schedule = modelMapper.map(dto, DoctorSchedule.class);
        doctor.addDoctorSchedule(schedule);
        return convertDoctorToDto(doctor);
    }

    @Override
    @Transactional
    public DoctorDto removeSchedule(Long doctorId, Long scheduleId) {
        Doctor doctor = getDoctorEntity(doctorId);
        doctor.removeScheduleById(scheduleId);
        return convertDoctorToDto(doctor);
    }

    @Override
    @Transactional
    public DoctorDto updateSchedule(Long doctorId, DoctorScheduleDto dto) {
        Doctor doctor = getDoctorEntity(doctorId);
        doctor.updateSchedule(dto.getId(), schedule -> {
            modelMapper.map(dto, schedule);
        });
        return convertDoctorToDto(doctor);
    }

    // Holiday Schedule operations
    @Override
    @Transactional
    public DoctorDto addHolidaySchedule(Long doctorId, DoctorHolidayScheduleDto dto) {
        Doctor doctor = getDoctorEntity(doctorId);
        DoctorHolidaySchedule holidaySchedule = modelMapper.map(dto, DoctorHolidaySchedule.class);
        doctor.addDoctorHolidaySchedule(holidaySchedule);
        return convertDoctorToDto(doctor);
    }

    @Override
    @Transactional
    public DoctorDto removeHolidaySchedule(Long doctorId, Long holidayScheduleId) {
        Doctor doctor = getDoctorEntity(doctorId);
        doctor.removeHolidayScheduleById(holidayScheduleId);
        return convertDoctorToDto(doctor);
    }

    @Override
    @Transactional
    public DoctorDto updateHolidaySchedule(Long doctorId, DoctorHolidayScheduleDto dto) {
        Doctor doctor = getDoctorEntity(doctorId);
        doctor.updateHolidaySchedule(dto.getId(), holidaySchedule -> {
            modelMapper.map(dto, holidaySchedule);
        });
        return convertDoctorToDto(doctor);
    }

    // Insurance Company operations
    @Override
    @Transactional
    public DoctorDto addInsuranceCompany(Long doctorId, InsuranceCompanyDto dto) {
        Doctor doctor = getDoctorEntity(doctorId);
        InsuranceCompany company = modelMapper.map(dto, InsuranceCompany.class);
        doctor.addInsuranceCompany(company);
        return convertDoctorToDto(doctor);
    }

    @Override
    public DoctorDto removeInsuranceCompany(Long doctorId, Long insuranceCompanyId) {
        return null;
    }


    // Getter methods for all relationships
    @Override
    public List<SpecializationsDto> getSpecializations(Long doctorId) {
        Doctor doctor = getDoctorEntity(doctorId);
        return doctor.getSpecialization().stream()
                .map(sp -> modelMapper.map(sp, SpecializationsDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ExperienceDto> getExperiences(Long doctorId) {
        Doctor doctor = getDoctorEntity(doctorId);
        return doctor.getExperiences().stream()
                .map(exp -> modelMapper.map(exp, ExperienceDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ScientificBackgroundDto> getScientificBackgrounds(Long doctorId) {
        Doctor doctor = getDoctorEntity(doctorId);
        return doctor.getScientificBackgrounds().stream()
                .map(sb -> modelMapper.map(sb, ScientificBackgroundDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorServiceDto> getServices(Long doctorId) {
        Doctor doctor = getDoctorEntity(doctorId);
        return doctor.getServices().stream()
                .map(s -> modelMapper.map(s, DoctorServiceDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorScheduleDto> getSchedules(Long doctorId) {
        Doctor doctor = getDoctorEntity(doctorId);
        return doctor.getDoctorSchedules().stream()
                .map(s -> modelMapper.map(s, DoctorScheduleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorHolidayScheduleDto> getHolidaySchedules(Long doctorId) {
        Doctor doctor = getDoctorEntity(doctorId);
        return doctor.getHolidaySchedules().stream()
                .map(h -> modelMapper.map(h, DoctorHolidayScheduleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<InsuranceCompanyDto> getInsuranceCompanies(Long doctorId) {
        Doctor doctor = getDoctorEntity(doctorId);
        return doctor.getInsuranceCompanies().stream()
                .map(i -> modelMapper.map(i, InsuranceCompanyDto.class))
                .collect(Collectors.toList());
    }

    public List<DoctorHolidaySchedule> findHolidayScheduleByDate(Long doctorId, LocalDateTime start){
        return doctorRepository.findHolidayScheduleByDate(doctorId,start);
    }

    public Doctor getDoctorEntity(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID " + id + " not found"));
    }
}