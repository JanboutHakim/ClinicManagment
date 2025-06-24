package com.example.DocLib.services.interfaces;

import com.example.DocLib.dto.appointment.AppointmentDto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.DocLib.dto.appointment.AppointmentResponseDto;
import com.example.DocLib.enums.AppointmentStatus;


public interface AppointmentServices {
        // Core CRUD Operations
        AppointmentResponseDto addAppointment(AppointmentDto appointmentDto);
        AppointmentResponseDto getAppointmentById(Long appointmentId);
        void deleteAppointment(Long appointmentId);

        // Booking Management
        AppointmentResponseDto cancelAppointmentByClinic(Long appointmentId, String cancellationReason);
        AppointmentResponseDto cancelAppointmentByPatient(Long appointmentId, String cancellationReason);
        AppointmentResponseDto rescheduleAppointment(Long appointmentId, AppointmentDto appointmentDto);
        AppointmentResponseDto confirmAppointment(Long appointmentId);

        // Status Management
        AppointmentResponseDto updateAppointmentStatus(Long appointmentId, AppointmentStatus status);

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
        List<AppointmentResponseDto> getCancelledAppointments(Long doctorId);
        int getAppointmentCountByStatus(AppointmentStatus status);
        double getDoctorCancellationRate(Long doctorId);

        // Patient History
        List<AppointmentResponseDto> getPatientHistory(Long patientId, int monthsBack);

        //Searching
        List<AppointmentResponseDto> searchDoctorAppointment(String q);
    }


