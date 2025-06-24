package com.example.DocLib.controllers;

import com.example.DocLib.dto.StringDto;
import com.example.DocLib.dto.appointment.AppointmentDto;
import com.example.DocLib.dto.appointment.AppointmentResponseDto;
import com.example.DocLib.dto.appointment.LocalDateTimeBlock;
import com.example.DocLib.dto.appointment.MonthsDto;
import com.example.DocLib.enums.AppointmentStatus;
import com.example.DocLib.exceptions.custom.AccessDeniedException;
import com.example.DocLib.services.implementation.AppointmentServicesImp;
import com.example.DocLib.services.implementation.AuthServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentServicesImp appointmentServicesImp;
    @Autowired
    public AppointmentController(AppointmentServicesImp appointmentServicesImp) {
        this.appointmentServicesImp = appointmentServicesImp;
    }

    private void checkAuthenticatedUser(Long id){
        if(!AuthServices.getCurrentUserId().equals(id))
            throw new AccessDeniedException("You are not allowed to update this user.");
    }
    private void checkAuthenticatedDoctor(Long id){
        AuthServices.getCurrentUserRole().stream()
                .map(org.springframework.security.core.GrantedAuthority::getAuthority)
                .filter(role -> role.equals("ROLE_DOCTOR"))
                .findFirst()
                .orElseThrow(() -> new AccessDeniedException("You are not allowed to update this user."));
    }
    private void checkAuthenticatedPatient(Long id){
        AuthServices.getCurrentUserRole().stream()
                .map(org.springframework.security.core.GrantedAuthority::getAuthority)
                .filter(role -> role.equals("ROLE_PATIENT"))
                .findFirst()
                .orElseThrow(() -> new AccessDeniedException("You are not allowed to update this user."));
    }



    /**
     * Retrieves an appointment by its ID.
     *
     * @param appointmentId The ID of the appointment to retrieve.
     * @return ResponseEntity containing the AppointmentDto representing the appointment.
     */
    @GetMapping("/{id}/{appointmentId}")
    public ResponseEntity<AppointmentResponseDto> getAppointmentById(@PathVariable Long id,@PathVariable Long appointmentId) {
        checkAuthenticatedUser(id);
        AppointmentResponseDto appointmentDto = appointmentServicesImp.getAppointmentById(appointmentId);
        return ResponseEntity.ok(appointmentDto);
    }

    /**
     * Creates a new appointment based on the provided AppointmentDto.
     *
     * @param appointmentDto The AppointmentDto object containing appointment details.
     * @return ResponseEntity with the created AppointmentDto object and HTTP status code 201 (CREATED).
     */
    @PostMapping("/{id}")
    public ResponseEntity<AppointmentResponseDto> addAppointment(@PathVariable Long id,@Valid @RequestBody AppointmentDto appointmentDto) {
        checkAuthenticatedUser(id);
        AppointmentResponseDto createdAppointment = appointmentServicesImp.addAppointment(appointmentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
    }

    /**
     * Deletes an appointment by the provided appointment ID.
     *
     * @param appointmentId the ID of the appointment to be deleted
     * @return ResponseEntity indicating the success of the operation with no content
     */
    @DeleteMapping("/{id}/{appointmentId}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long appointmentId, @PathVariable Long id) {
        checkAuthenticatedUser(id);
        appointmentServicesImp.deleteAppointment(appointmentId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Updates the status of an appointment by rescheduling it with a new appointmentDto.
     *
     * @param appointmentId The ID of the appointment to be updated.
     * @param appointmentDto The AppointmentDto object containing the new information for rescheduling.
     * @return ResponseEntity containing the updated AppointmentDto with the new status.
     */
    @PutMapping("/{id}/reschedule/{appointmentId}")
    public ResponseEntity<AppointmentResponseDto> updateAppointmentStatus(@PathVariable Long id,@PathVariable Long appointmentId, @RequestBody @Valid AppointmentDto appointmentDto) {
        checkAuthenticatedUser(id);
        AppointmentResponseDto updatedAppointment = appointmentServicesImp.rescheduleAppointment(appointmentId, appointmentDto);
        return ResponseEntity.ok(updatedAppointment);
    }

    /**
     * Retrieves a list of appointments associated with a specific doctor.
     *
     * @param id The unique identifier of the doctor for whom appointments are to be fetched.
     * @return ResponseEntity<List < AppointmentDto>> A response entity containing a list of AppointmentDto objects
     */
    @GetMapping("/doctor/{id}")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentsByDoctor( @PathVariable Long id) {
        checkAuthenticatedUser(id);
        List<AppointmentResponseDto> appointments = appointmentServicesImp.getAppointmentsByDoctor(id);
        return ResponseEntity.ok(appointments);
    }

    /**
     * Retrieves a list of appointments associated with a specific patient.
     *
     * @param id The ID of the patient for whom to retrieve appointments
     * @return A list of AppointmentDto objects representing appointments for the specified patient
     */
    @GetMapping("/patient/{id}")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentsByPatient( @PathVariable Long id) {
        checkAuthenticatedUser(id);
        List<AppointmentResponseDto> appointments = appointmentServicesImp.getAppointmentsByPatient(id);
        return ResponseEntity.ok(appointments);
    }

    /**
     * Retrieves a list of upcoming appointments for a specific doctor.
     *
     * @param doctorId The ID of the doctor for whom to retrieve upcoming appointments.
     * @return ResponseEntity with a list of AppointmentDto objects representing upcoming appointments for the specified doctor.
     */
    @GetMapping("/{id}/doctor/{doctorId}/upcoming-appointments")
    public ResponseEntity<List<AppointmentResponseDto>> getUpcomingAppointmentsForDoctor(@PathVariable Long doctorId, @PathVariable Long id) {
        checkAuthenticatedUser(id);
        List<AppointmentResponseDto> appointments = appointmentServicesImp.getUpcomingAppointmentsForDoctor(doctorId);
        return ResponseEntity.ok(appointments);
    }

    /**
     * Retrieve a list of upcoming appointments for a specific patient.
     *
     * @param patientId The ID of the patient for whom to fetch the upcoming appointments.
     * @return ResponseEntity containing a List of AppointmentDto objects representing the upcoming appointments for the patient.
     */
    @GetMapping("/{id}/patient/{patientId}/upcoming-appointments")
    public ResponseEntity<List<AppointmentResponseDto>> getUpcomingAppointmentsForPatient(@PathVariable Long patientId, @PathVariable Long id) {
        checkAuthenticatedUser(id);
        List<AppointmentResponseDto> appointments = appointmentServicesImp.getUpcomingAppointmentsForPatient(patientId);
        return ResponseEntity.ok(appointments);
    }

    /**
     * Retrieves the available slots for a specific doctor based on their weekly schedule.
     *
     * @param doctorId The ID of the doctor for whom the available slots are being retrieved.
     * @return A list of LocalDateTime objects representing the available slots for the specified doctor.
     */
    @GetMapping("/doctor/{doctorId}/available-slots")
    public ResponseEntity<List<LocalDateTime>> getDoctorAvailableSlots(@PathVariable Long doctorId) {
        List<LocalDateTime> availableSlots = appointmentServicesImp.getDoctorAvailableSlots(doctorId);
        return ResponseEntity.ok(availableSlots);
    }

    /**
     * Confirms the appointment with the specified appointmentId.
     *
     * @param appointmentId The ID of the appointment to confirm.
     * @return ResponseEntity containing the confirmed AppointmentDto.
     */
    @PutMapping("/{id}/confirm/{appointmentId}")
    public ResponseEntity<AppointmentResponseDto> confirmAppointment(@PathVariable Long appointmentId, @PathVariable Long id) {
        checkAuthenticatedDoctor(id);
        AppointmentResponseDto confirmedAppointment = appointmentServicesImp.confirmAppointment(appointmentId);
        return ResponseEntity.ok(confirmedAppointment);
    }

    /**
     *
     */
    @PutMapping("/{id}/{appointmentId}/cancel-by-doctor")
    public ResponseEntity<AppointmentResponseDto> cancelAppointmentByDoctor(
            @PathVariable Long appointmentId,
            @RequestBody StringDto cancellationReason, @PathVariable Long id) {
        checkAuthenticatedUser(id);
        AppointmentResponseDto canceled = appointmentServicesImp.cancelAppointmentByClinic(appointmentId, cancellationReason.getName());
        return ResponseEntity.ok(canceled);
    }

    /**
     * Cancels an appointment by the patient.
     *
     * @param appointmentId The ID of the appointment to be canceled.
     * @param cancellationReason The reason for canceling the appointment.
     * @return A ResponseEntity containing the AppointmentDto of the canceled appointment.
     */
    @PutMapping("/{id}/{appointmentId}/cancel-by-patient")
    public ResponseEntity<AppointmentResponseDto> cancelAppointmentByPatient(
            @PathVariable Long appointmentId,
            @RequestBody StringDto cancellationReason, @PathVariable Long id) {
        checkAuthenticatedUser(id);
        AppointmentResponseDto canceled = appointmentServicesImp.cancelAppointmentByPatient(appointmentId, cancellationReason.getName());
        return ResponseEntity.ok(canceled);
    }

    /**
     * Updates the status of an appointment identified by the given appointmentId.
     *
     * @param appointmentId the ID of the appointment to update
     * @param status the new status to set for the appointment
     * @return ResponseEntity containing the updated AppointmentDto object with the new status
     */
    @PutMapping("/{appointmentId}/status")
    public ResponseEntity<AppointmentResponseDto> updateAppointmentStatus(
            @PathVariable Long appointmentId,
            @RequestParam AppointmentStatus status) {
        AppointmentResponseDto updated = appointmentServicesImp.updateAppointmentStatus(appointmentId, status);
        return ResponseEntity.ok(updated);
    }

    /**
     * Get appointments filtered by a specific status.
     *
     * @param status The status by which to filter the appointments.
     * @return ResponseEntity containing a list of AppointmentDto objects filtered by the specified status.
     */
    @GetMapping("/{id}/status/{status}")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentsByStatus(@PathVariable AppointmentStatus status, @PathVariable Long id) {
        checkAuthenticatedUser(id);
        List<AppointmentResponseDto> appointments = appointmentServicesImp.getAppointmentsByStatus(status);
        return ResponseEntity.ok(appointments);
    }

    /**
     * Retrieves a list of appointments within the specified date range.
     *
     * @param timeBlock The LocalDateTimeBlock object containing the start and end timestamps.
     * @return ResponseEntity with a list of AppointmentDto objects representing appointments within the date range.
     */
    @GetMapping("/{id}/date-range")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentsByDateRange(@RequestBody @Valid LocalDateTimeBlock timeBlock, @PathVariable Long id) {
        checkAuthenticatedUser(id);
        List<AppointmentResponseDto> appointments = appointmentServicesImp.getAppointmentsByDateRange(timeBlock.getStart(), timeBlock.getEnd());
        return ResponseEntity.ok(appointments);
    }

    /**
     * Check for upcoming appointments and send reminder notifications to patients.
     * This method is triggered periodically at a fixed rate.
     * The upcoming appointments are identified within a specific time range and a reminder message is sent to the respective patient.
     *
     * @return ResponseEntity<Void> indicating the success of checking upcoming appointments.
     */
    @PostMapping("/check-upcoming-appointments")
    public ResponseEntity<Void> checkUpcomingAppointments() {
        appointmentServicesImp.checkUpcomingAppointments();
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieve a list of cancelled appointments for a specific doctor.
     *
     * @param doctorId The unique identifier of the doctor for whom to retrieve cancelled appointments.
     * @return ResponseEntity<List < AppointmentDto>> A list of AppointmentDto objects representing the cancelled appointments.
     */
    @GetMapping("/{id}/doctor/{doctorId}/cancelled-appointments")
    public ResponseEntity<List<AppointmentResponseDto>> getCancelledAppointments(@PathVariable Long doctorId, @PathVariable Long id) {
        checkAuthenticatedUser(id);
        List<AppointmentResponseDto> cancelledAppointments = appointmentServicesImp.getCancelledAppointments(doctorId);
        return ResponseEntity.ok(cancelledAppointments);
    }

    /**
     * Retrieves the count of appointments based on the provided status.
     *
     * @param status The status of appointments to count
     * @return The total count of appointments with the specified status
     */
    @GetMapping("/{id}/count-by-status")
    public ResponseEntity<Integer> getAppointmentCountByStatus(@RequestParam AppointmentStatus status, @PathVariable Long id) {
        checkAuthenticatedUser(id);
        int count = appointmentServicesImp.getAppointmentCountByStatus(status);
        return ResponseEntity.ok(count);
    }

    /**
     * Retrieves the cancellation rate for a specific doctor based on their appointment data.
     *
     * @param doctorId the ID of the doctor for whom to retrieve the cancellation rate
     * @return the cancellation rate as a Double value
     */
    @GetMapping("/{id}/doctor/{doctorId}/cancellation-rate")
    public ResponseEntity<Double> getDoctorCancellationRate(@PathVariable Long doctorId, @PathVariable Long id) {
        checkAuthenticatedUser(id);
        double rate = appointmentServicesImp.getDoctorCancellationRate(doctorId);
        return ResponseEntity.ok(rate);
    }

    /**
     *
     * Retrieves the history of appointments for a specific patient within a specified number of months in the past.
     *
     * @param patientId The ID of the patient for whom to retrieve the appointment history.
     * @param monthsBack The MonthsDto object containing the number of months in the past to retrieve history.
     * @return ResponseEntity containing a list of AppointmentDto objects representing the patient's appointment history.
     */
    @GetMapping("/patient/{patientId}/history")
    public ResponseEntity<List<AppointmentResponseDto>> getPatientHistory(
            @PathVariable Long patientId,
            @RequestBody @Valid MonthsDto monthsBack) {
        checkAuthenticatedUser(patientId);
        List<AppointmentResponseDto> history = appointmentServicesImp.getPatientHistory(patientId, monthsBack.getMonths());
        return ResponseEntity.ok(history);
    }



}
