package com.example.DocLib.services.interfaces;

import com.example.DocLib.dto.appointment.AppointmentDto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.DocLib.dto.appointment.AppointmentResponseDto;
import com.example.DocLib.enums.AppointmentStatus;


public interface AppointmentServices {
        // Core CRUD Operations
        AppointmentDto addAppointment(AppointmentDto appointmentDto);
        AppointmentDto getAppointmentById(Long appointmentId);
        void deleteAppointment(Long appointmentId);

        // Booking Management
        AppointmentResponseDto cancelAppointmentByClinic(Long appointmentId, String cancellationReason);
        AppointmentDto cancelAppointmentByPatient(Long appointmentId, String cancellationReason);
        AppointmentDto rescheduleAppointment(Long appointmentId, AppointmentDto appointmentDto);
        AppointmentDto confirmAppointment(Long appointmentId);

        // Status Management
        AppointmentDto updateAppointmentStatus(Long appointmentId, AppointmentStatus status);

        // Query Operations
        List<AppointmentResponseDto> getAppointmentsByDoctor(Long doctorId);
        List<AppointmentResponseDto> getAppointmentsByPatient(Long patientId);
        List<AppointmentResponseDto> getAppointmentsByStatus(AppointmentStatus status);
        List<AppointmentResponseDto> getAppointmentsByDateRange(LocalDateTime start, LocalDateTime end);
        List<AppointmentResponseDto> getUpcomingAppointmentsForPatient(Long doctorId);
        List<AppointmentResponseDto> getUpcomingAppointmentsForDoctor(Long doctorId);

        // Availability Checking
        boolean isPatientAvailable(Long patientId, Long doctorId, LocalDateTime startTime);
        boolean isDoctorAvailable(Long doctorId, LocalDateTime dateTime);
        boolean isDoctorOnVacation(Long doctorId, LocalDateTime startTime);
        List<LocalDateTime> getDoctorAvailableSlots(Long doctorId);

        // Notification System
        void checkUpcomingAppointments();
        void sendAppointmentReminders();

        // Reporting
        List<AppointmentDto> getCancelledAppointments(Long doctorId);
        int getAppointmentCountByStatus(AppointmentStatus status);
        double getDoctorCancellationRate(Long doctorId);

        // Patient History
        List<AppointmentDto> getPatientHistory(Long patientId, int monthsBack);

        //Searching
        List<AppointmentResponseDto> searchDoctorAppointment(String q);
    }


