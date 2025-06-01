package com.example.DocLib.services.interfaces;

import com.example.DocLib.dto.appointment.AppointmentDto;

import java.time.LocalDateTime;
import java.util.List;
import com.example.DocLib.enums.AppointmentStatus;


public interface AppointmentServices {
        // Core CRUD Operations
        AppointmentDto createAppointment(AppointmentDto appointmentDto);
        AppointmentDto getAppointmentById(Long appointmentId);
        void deleteAppointment(Long appointmentId);

        // Booking Management
        AppointmentDto cancelAppointmentByClinic(Long appointmentId, String cancellationReason);
        AppointmentDto cancelAppointmentByPatient(Long appointmentId, String cancellationReason);
        AppointmentDto rescheduleAppointment(Long appointmentId, AppointmentDto appointmentDto);
        AppointmentDto confirmAppointment(Long appointmentId);

        // Status Management
        AppointmentDto updateAppointmentStatus(Long appointmentId, AppointmentStatus status);

        // Query Operations
        List<AppointmentDto> getAppointmentsByDoctor(Long doctorId);
        List<AppointmentDto> getAppointmentsByPatient(Long patientId);
        List<AppointmentDto> getAppointmentsByStatus(AppointmentStatus status);
        List<AppointmentDto> getAppointmentsByDateRange(LocalDateTime start, LocalDateTime end);
        List<AppointmentDto> getUpcomingAppointmentsForPatient(Long doctorId);
        List<AppointmentDto> getUpcomingAppointmentsForDoctor(Long doctorId);

        // Availability Checking
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
    }


