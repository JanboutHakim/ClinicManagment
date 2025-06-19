package com.example.DocLib.models.doctor;

import com.example.DocLib.exceptions.custom.ResourceNotFoundException;
import com.example.DocLib.models.appointment.Appointment;
import com.example.DocLib.models.InsuranceCompany;
import com.example.DocLib.models.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Entity
@Table(name = "doctor")
@Getter
@Setter
@NoArgsConstructor
public class Doctor {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    private String clinicName;

    private String address;

    private int checkupDurationInMinutes;

    private int yearsOfExperience;

    private double checkupPrice;

    @Column(nullable = false)
    private String unionMembershipNumber;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Specializations> specialization = new ArrayList<>();


    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments = new ArrayList<>();


    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experiences = new ArrayList<>();


    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScientificBackground> scientificBackgrounds = new ArrayList<>();



    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DoctorService> services = new ArrayList<>();



    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DoctorSchedule> doctorSchedules = new ArrayList<>();



    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DoctorHolidaySchedule> holidaySchedules = new ArrayList<>();


    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<InsuranceCompany> insuranceCompanies = new ArrayList<>();
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        appointment.setDoctor(this);
    }

    public void removeAppointment(Appointment appointment) {
        appointments.remove(appointment);
        appointment.setDoctor(null);
    }
    public void addInsuranceCompany(InsuranceCompany insuranceCompany) {
        insuranceCompanies.add(insuranceCompany);
        insuranceCompany.setDoctor(this);
    }
    public void addService(DoctorService service) {
        services.add(service);
        service.setDoctor(this);
    }
    public void addScientificBackground(ScientificBackground scientificBackground) {
        scientificBackgrounds.add(scientificBackground);
        scientificBackground.setDoctor(this);
    }
    public void addSpecialization(Specializations specializations) {
        specialization.add(specializations);
        specializations.setDoctor(this);
    }


    public void addDoctorHolidaySchedule(DoctorHolidaySchedule doctorHolidaySchedule) {
        holidaySchedules.add(doctorHolidaySchedule);
        doctorHolidaySchedule.setDoctor(this);
    }



    // Helper methods for managing relationships
    public void addExperience(Experience experience) {
        experiences.add(experience);
        experience.setDoctor(this);
    }
    public void addDoctorSchedule(DoctorSchedule doctorSchedule) {
        doctorSchedules.add(doctorSchedule);
        doctorSchedule.setDoctor(this);
    }

    public void updateExperience(Long experienceId, Consumer<Experience> updater) {
        Experience experience = experiences.stream()
                .filter(exp -> exp.getId().equals(experienceId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Experience with ID " + experienceId + " not found"));
        updater.accept(experience);
    }

    public void removeExperienceById(Long experienceId) {
        Experience experience = experiences.stream()
                .filter(exp -> exp.getId().equals(experienceId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Experience with ID " + experienceId + " not found"));
        experiences.remove(experience);
        experience.setDoctor(null);
    }

    public void updateScientificBackground(Long backgroundId, Consumer<ScientificBackground> updater) {
        ScientificBackground background = scientificBackgrounds.stream()
                .filter(bg -> bg.getId().equals(backgroundId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("ScientificBackground with ID " + backgroundId + " not found"));
        updater.accept(background);
    }

    public void removeScientificBackgroundById(Long backgroundId) {
        ScientificBackground background = scientificBackgrounds.stream()
                .filter(bg -> bg.getId().equals(backgroundId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("ScientificBackground with ID " + backgroundId + " not found"));
        scientificBackgrounds.remove(background);
        background.setDoctor(null);
    }

    public void updateSpecialization(Long specializationId, Consumer<Specializations> updater) {
        Specializations sp = specialization.stream()
                .filter(s -> s.getId().equals(specializationId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Specialization with ID " + specializationId + " not found"));
        updater.accept(sp);
    }

    public void removeSpecializationById(Long specializationId) {
        Specializations sp = specialization.stream()
                .filter(s -> s.getId().equals(specializationId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Specialization with ID " + specializationId + " not found"));
        specialization.remove(sp);
        sp.setDoctor(null);
    }

    public void updateService(Long serviceId, Consumer<DoctorService> updater) {
        DoctorService service = services.stream()
                .filter(s -> s.getId().equals(serviceId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Service with ID " + serviceId + " not found"));
        updater.accept(service);
    }

    public void removeServiceById(Long serviceId) {
        DoctorService service = services.stream()
                .filter(s -> s.getId().equals(serviceId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Service with ID " + serviceId + " not found"));
        services.remove(service);
        service.setDoctor(null);
    }

    public void updateSchedule(Long scheduleId, Consumer<DoctorSchedule> updater) {
        DoctorSchedule schedule = doctorSchedules.stream()
                .filter(s -> s.getId().equals(scheduleId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Schedule with ID " + scheduleId + " not found"));
        updater.accept(schedule);
    }

    public void removeScheduleById(Long scheduleId) {
        DoctorSchedule schedule = doctorSchedules.stream()
                .filter(s -> s.getId().equals(scheduleId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Schedule with ID " + scheduleId + " not found"));
        doctorSchedules.remove(schedule);
        schedule.setDoctor(null);
    }

    public void updateHolidaySchedule(Long holidayScheduleId, Consumer<DoctorHolidaySchedule> updater) {
        DoctorHolidaySchedule holidaySchedule = holidaySchedules.stream()
                .filter(h -> h.getId().equals(holidayScheduleId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Holiday Schedule with ID " + holidayScheduleId + " not found"));
        updater.accept(holidaySchedule);
    }

    public void removeHolidayScheduleById(Long holidayScheduleId) {
        DoctorHolidaySchedule holidaySchedule = holidaySchedules.stream()
                .filter(h -> h.getId().equals(holidayScheduleId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Holiday Schedule with ID " + holidayScheduleId + " not found"));
        holidaySchedules.remove(holidaySchedule);
        holidaySchedule.setDoctor(null);
    }





}