package com.example.DocLib.controllers;

import com.example.DocLib.dto.AddressDto;
import com.example.DocLib.dto.InsuranceCompanyDto;
import com.example.DocLib.dto.doctor.*;
import com.example.DocLib.services.implementation.AppointmentServicesImp;
import com.example.DocLib.services.implementation.DoctorServicesImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    private final DoctorServicesImp doctorServicesImp;
    private final AppointmentServicesImp appointmentServicesImp;
    @Autowired
    public DoctorController(DoctorServicesImp doctorServicesImp, AppointmentServicesImp appointmentServicesImp) {
        this.doctorServicesImp = doctorServicesImp;
        this.appointmentServicesImp = appointmentServicesImp;
    }

    /**
     * Retrieves a doctor by their ID.
     *
     * @param id The ID of the doctor to retrieve.
     * @return ResponseEntity containing the DoctorDto object representing the retrieved doctor.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable Long id) {
        DoctorDto doctorDto = doctorServicesImp.getDoctorById(id);
        return ResponseEntity.ok(doctorDto);
    }
    /**
     * Retrieves a list of all doctors.
     *
     * @return A ResponseEntity containing a list of DoctorDto objects representing all doctors.
     */
    @GetMapping("/all")
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        List<DoctorDto> doctors = doctorServicesImp.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    /**
     */
    @PostMapping("/{id}/address")
    public ResponseEntity<DoctorDto> updateAddress(@PathVariable Long id, @RequestBody AddressDto addressDto) {
        DoctorDto updatedDoctor = doctorServicesImp.updateAddress(id, addressDto.getAddress());
        return ResponseEntity.ok(updatedDoctor);
    }

    /**
     *
     */
    @PostMapping("/{id}/specialization")
    public ResponseEntity<DoctorDto> addSpecialization(@PathVariable Long id, @RequestBody @Valid SpecializationsDto specializationDto) {
        DoctorDto updatedDoctor = doctorServicesImp.addSpecialization(id, specializationDto);
        return ResponseEntity.ok(updatedDoctor);
    }

    /**
     *
     */
    @PostMapping("/{id}/years-of-experience")
    public ResponseEntity<DoctorDto> updateYearsOfExperience(@PathVariable Long id, @RequestBody ExperienceYearsDto yearsDto) {
        DoctorDto updatedDoctor = doctorServicesImp.updateYearsOfExperience(id, yearsDto.getYears());
        return ResponseEntity.ok(updatedDoctor);
    }

    /**
     * Updates the union membership number for a doctor with the given ID.
     *
     * @param id The ID of the doctor to update the union membership number for.
     * @param unionMembershipNumber The new union membership number to assign to the doctor.
     * @return ResponseEntity containing the updated DoctorDto with the new union membership number.
     */
    @PostMapping("/{id}/union-membership-number")
    public ResponseEntity<DoctorDto> updateUnionMembershipNumber(@PathVariable Long id, @RequestBody String unionMembershipNumber) {
        DoctorDto updatedDoctor = doctorServicesImp.updateUnionMembershipNumber(id, unionMembershipNumber);
        return ResponseEntity.ok(updatedDoctor);
    }
    /**
     *
     */
    @GetMapping("/{doctorId}/experiences")
    public ResponseEntity<List<ExperienceDto>> getExperiences(@PathVariable Long doctorId) {
        List<ExperienceDto> experiences = doctorServicesImp.getExperiences(doctorId);
        return ResponseEntity.ok(experiences);
    }

    /**
     *
     */
    @PostMapping("/{doctorId}/experiences")
    public ResponseEntity<DoctorDto> addExperience(@PathVariable Long doctorId, @RequestBody @Valid ExperienceDto experienceDto) {
        System.out.println(experienceDto.toString());
        DoctorDto updatedDoctor = doctorServicesImp.addExperience(doctorId, experienceDto);
        return ResponseEntity.ok(updatedDoctor);
    }
    /**
     *
     */
    @PostMapping("/{doctorId}/experiences/delete/{experienceId}")
    public ResponseEntity<DoctorDto> removeExperience(@PathVariable Long doctorId, @PathVariable  Long experienceId) {
        DoctorDto updatedDoctor = doctorServicesImp.removeExperience(doctorId, experienceId);
        return ResponseEntity.ok(updatedDoctor);
    }
    /**
     *
     */
    @PostMapping("/{doctorId}/experiences/update")
    public ResponseEntity<DoctorDto> updateExperience(@PathVariable Long doctorId, @RequestBody @Valid ExperienceDto experienceDto) {
        DoctorDto updatedDoctor = doctorServicesImp.updateExperience(doctorId, experienceDto);
        return ResponseEntity.ok(updatedDoctor);
    }
    /**
     *
     */
    @GetMapping("/{doctorId}/scientific-backgrounds")
    public ResponseEntity<List<ScientificBackgroundDto>> getScientificBackgrounds(@PathVariable Long doctorId) {
        List<ScientificBackgroundDto> backgrounds = doctorServicesImp.getScientificBackgrounds(doctorId);
        return ResponseEntity.ok(backgrounds);
    }
    /**
     *
     */
    @PostMapping("/{doctorId}/scientific-backgrounds")
    public ResponseEntity<DoctorDto> addScientificBackground(@PathVariable Long doctorId, @RequestBody @Valid ScientificBackgroundDto dto) {
        DoctorDto updatedDoctor = doctorServicesImp.addScientificBackground(doctorId, dto);
        return ResponseEntity.ok(updatedDoctor);
    }
    /**
     * Removes a scientific background from a doctor.
     *
     * @param doctorId The ID of the doctor from which to remove the scientific background
     * @param scientificBackgroundId The ID of the scientific background to be removed
     * @return ResponseEntity containing the updated DoctorDto after removing the scientific background
     */
    @PostMapping("/{doctorId}/scientific-backgrounds/delete/{scientificBackgroundId}")
    public ResponseEntity<DoctorDto> removeScientificBackground(@PathVariable Long doctorId, @PathVariable Long scientificBackgroundId) {
        DoctorDto updatedDoctor = doctorServicesImp.removeScientificBackground(doctorId, scientificBackgroundId);
        return ResponseEntity.ok(updatedDoctor);
    }
    /**
     *
     */
    @GetMapping("/{doctorId}/services")
    public ResponseEntity<List<DoctorServiceDto>> getServices(@PathVariable Long doctorId) {
        List<DoctorServiceDto> services = doctorServicesImp.getServices(doctorId);
        return ResponseEntity.ok(services);
    }
    /**
     *
     */
    @PostMapping("/{doctorId}/services")
    public ResponseEntity<DoctorDto> addService(@PathVariable Long doctorId, @RequestBody @Valid DoctorServiceDto dto) {
        DoctorDto updatedDoctor = doctorServicesImp.addService(doctorId, dto);
        return ResponseEntity.ok(updatedDoctor);
    }
    /**
     *
     */
    @PostMapping("/{doctorId}/services/delete/{serviceId}")
    public ResponseEntity<DoctorDto> removeService(@PathVariable Long doctorId, @PathVariable Long serviceId) {
        DoctorDto updatedDoctor = doctorServicesImp.removeService(doctorId, serviceId);
        return ResponseEntity.ok(updatedDoctor);
    }
    /**
     *
     */
    @PostMapping("/{doctorId}/services/update")
    public ResponseEntity<DoctorDto> updateService(@PathVariable Long doctorId, @RequestBody @Valid DoctorServiceDto dto) {
        DoctorDto updatedDoctor = doctorServicesImp.updateService(doctorId, dto);
        return ResponseEntity.ok(updatedDoctor);
    }

    /**
     * Retrieves the schedules of a specific doctor based on the provided doctorId.
     *
     * @param doctorId The ID of the doctor whose schedules are to be retrieved.
     * @return ResponseEntity containing a list of DoctorScheduleDto objects representing the schedules of the doctor.
     */
    @GetMapping("/{doctorId}/schedules")
    public ResponseEntity<List<DoctorScheduleDto>> getSchedules(@PathVariable Long doctorId) {
        List<DoctorScheduleDto> schedules = doctorServicesImp.getSchedules(doctorId);
        return ResponseEntity.ok(schedules);
    }
    /**
     *
     */
    @GetMapping("/{doctorId}/holiday-schedules")
    public ResponseEntity<List<DoctorHolidayScheduleDto>> getHolidaySchedules(@PathVariable Long doctorId) {
        List<DoctorHolidayScheduleDto> holidaySchedules = doctorServicesImp.getHolidaySchedules(doctorId);
        return ResponseEntity.ok(holidaySchedules);
    }
    /**
     *
     */
    @PostMapping("/{doctorId}/holiday-schedules")
    public ResponseEntity<DoctorDto> addHolidaySchedule(@PathVariable Long doctorId, @RequestBody @Valid DoctorHolidayScheduleDto dto) {
        DoctorDto updatedDoctor = doctorServicesImp.addHolidaySchedule(doctorId, dto);
        return ResponseEntity.ok(updatedDoctor);
    }
    /**
     * Removes a holiday schedule for a specific doctor.
     *
     * @param doctorId the ID of the doctor
     * @param doctorHolidayScheduleId the ID of the holiday schedule to remove
     * @return ResponseEntity containing the updated DoctorDto after removing the holiday schedule
     */
    @PostMapping("/{doctorId}/holiday-schedules/delete/{doctorHolidayScheduleId}")
    public ResponseEntity<DoctorDto> removeHolidaySchedule(@PathVariable Long doctorId, @PathVariable Long doctorHolidayScheduleId) {
        DoctorDto updatedDoctor = doctorServicesImp.removeHolidaySchedule(doctorId, doctorHolidayScheduleId);
        return ResponseEntity.ok(updatedDoctor);
    }
    /**
     * Retrieve a list of Insurance Companies associated with a specific doctor.
     *
     * @param doctorId The ID of the doctor for which to retrieve insurance companies.
     * @return A ResponseEntity containing a list of InsuranceCompanyDto objects.
     */
    @GetMapping("/{doctorId}/insurance-companies")
    public ResponseEntity<List<InsuranceCompanyDto>> getInsuranceCompanies(@PathVariable Long doctorId) {
        List<InsuranceCompanyDto> companies = doctorServicesImp.getInsuranceCompanies(doctorId);
        return ResponseEntity.ok(companies);
    }

    /**
     *
     */
    @PostMapping("/{doctorId}/insurance-companies")
    public ResponseEntity<DoctorDto> addInsuranceCompany(@PathVariable Long doctorId, @RequestBody @Valid InsuranceCompanyDto dto) {
        DoctorDto updatedDoctor = doctorServicesImp.addInsuranceCompany(doctorId, dto);
        return ResponseEntity.ok(updatedDoctor);
    }

    /**
     *
     */
    @PostMapping("/{doctorId}/insurance-companies/{companyId}")
    public ResponseEntity<DoctorDto> removeInsuranceCompany(@PathVariable Long doctorId, @PathVariable  Long companyId) {
        DoctorDto updatedDoctor = doctorServicesImp.removeInsuranceCompany(doctorId, companyId);
        return ResponseEntity.ok(updatedDoctor);
    }


    /**
     * Adds a new schedule for a specific doctor.
     *
     * @param doctorId The ID of the doctor to add the schedule for.
     * @param scheduleDto The DoctorScheduleDto object representing the new schedule to add.
     * @return ResponseEntity with updated DoctorDto object after adding the schedule.
     */
    @PostMapping("/{doctorId}/schedules")
    public ResponseEntity<DoctorDto> addSchedule(@PathVariable Long doctorId, @RequestBody @Valid DoctorScheduleDto scheduleDto) {
        DoctorDto updatedDoctor = doctorServicesImp.addSchedule(doctorId, scheduleDto);
        return ResponseEntity.ok(updatedDoctor);
    }

    /**
     *
     */
    @PostMapping("/{doctorId}/schedules/delete/{doctorScheduleId}")
    public ResponseEntity<DoctorDto> removeSchedule(@PathVariable Long doctorId, @PathVariable Long doctorScheduleId) {
        DoctorDto updatedDoctor = doctorServicesImp.removeSchedule(doctorId, doctorScheduleId);
        return ResponseEntity.ok(updatedDoctor);
    }


    /**
     *
     */
    @GetMapping("/{doctorId}/appointments")
    public ResponseEntity<List<LocalDateTime>> getDoctorAvailableSlots(@PathVariable Long doctorId){
        List<LocalDateTime> availableSlots = appointmentServicesImp.getDoctorAvailableSlots(doctorId);
        return ResponseEntity.ok(availableSlots);
    }

    /**
     * Retrieves the list of specializations for a specific doctor.
     *
     * @param doctorId the identifier of the doctor whose specializations are being requested
     * @return a list of SpecializationsDto objects representing the specializations of the doctor
     */
    @GetMapping("/{doctorId}/specializations")
    public ResponseEntity<List<SpecializationsDto>> getSpecializations(@PathVariable Long doctorId) {
        List<SpecializationsDto> specializations = doctorServicesImp.getSpecializations(doctorId);
        return ResponseEntity.ok(specializations);
    }

    /**
     *
     */
    @PostMapping("/{doctorId}/specializations/delete/{specializationId}")
    public ResponseEntity<DoctorDto> removeSpecialization(@PathVariable Long doctorId, @PathVariable Long specializationId) {
        DoctorDto updatedDoctor = doctorServicesImp.removeSpecialization(doctorId, specializationId);
        return ResponseEntity.ok(updatedDoctor);
    }


    /**
     *
     */
    @PostMapping("/{doctorId}/specializations/update")
    public ResponseEntity<DoctorDto> updateSpecialization(@PathVariable Long doctorId, @RequestBody @Valid SpecializationsDto dto) {
        DoctorDto updatedDoctor = doctorServicesImp.updateSpecialization(doctorId, dto);
        return ResponseEntity.ok(updatedDoctor);
    }

    /**
     * Updates the scientific background of a doctor.
     *
     * @param doctorId the unique identifier of the doctor whose scientific background is to be updated
     * @param dto the ScientificBackgroundDto object containing the new scientific background details
     *
     * @return ResponseEntity with the updated DoctorDto object if the update was successful,
     *         or an error response if the update failed
     */
    @PutMapping("/{doctorId}/scientific-backgrounds/update")
    public ResponseEntity<DoctorDto> updateScientificBackground(@PathVariable Long doctorId, @RequestBody @Valid ScientificBackgroundDto dto) {
        DoctorDto updatedDoctor = doctorServicesImp.updateScientificBackground(doctorId, dto);
        return ResponseEntity.ok(updatedDoctor);
    }

    /**
     * Update the schedule of a doctor.
     *
     * @param doctorId the ID of the doctor whose schedule is to be updated
     * @param dto the DoctorScheduleDto containing the new schedule information
     *
     * @return ResponseEntity with the updated DoctorDto
     */
    @PutMapping("/{doctorId}/schedules/update")
    public ResponseEntity<DoctorDto> updateSchedule(@PathVariable Long doctorId, @RequestBody @Valid DoctorScheduleDto dto) {
        DoctorDto updatedDoctor = doctorServicesImp.updateSchedule(doctorId, dto);
        return ResponseEntity.ok(updatedDoctor);
    }

    /**
     * Updates the holiday schedule for a specific doctor.
     *
     * @param doctorId The ID of the doctor for which the holiday schedule is being updated.
     * @param dto The DoctorHolidayScheduleDto object containing the updated holiday schedule details.
     * @return ResponseEntity containing the updated DoctorDto object after updating the holiday schedule.
     */
    @PutMapping("/{doctorId}/holiday-schedules/update")
    public ResponseEntity<DoctorDto> updateHolidaySchedule(@PathVariable Long doctorId, @RequestBody @Valid DoctorHolidayScheduleDto dto) {
        DoctorDto updatedDoctor = doctorServicesImp.updateHolidaySchedule(doctorId, dto);
        return ResponseEntity.ok(updatedDoctor);
    }


}
