package com.example.DocLib.enums;

public enum AppointmentStatus {
    /**
     * Appointment has been created but not yet confirmed
     */
    REQUESTED,

    /**
     * Appointment has been confirmed by the clinic/staff
     */
    CONFIRMED,

    /**
     * Patient has checked in and is waiting
     */
    CHECKED_IN,

    /**
     * Doctor has started the consultation
     */
    IN_PROGRESS,

    /**
     * Appointment was successfully completed
     */
    COMPLETED,

    /**
     * Patient didn't show up without cancellation
     */
    NO_SHOW,

    /**
     * Appointment was cancelled by the patient
     */
    CANCELLED_BY_PATIENT,

    /**
     * Appointment was cancelled by the doctor/clinic
     */
    CANCELLED_BY_CLINIC,

    /**
     * Appointment was rescheduled to a new time
     */
    RESCHEDULED,

    /**
     * Payment is pending for the appointment
     */
    PAYMENT_PENDING,

    /**
     * Appointment is on hold waiting for lab results
     */
    ON_HOLD,

    /**
     * Telemedicine/virtual appointment
     */
    VIRTUAL,

    /**
     * In-person physical appointment
     */
    IN_PERSON;

    /**
     * Check if the status represents an active appointment
     */
    public boolean isActive() {
        return this == REQUESTED ||
                this == CONFIRMED ||
                this == CHECKED_IN ||
                this == IN_PROGRESS ||
                this == PAYMENT_PENDING;
    }

    /**
     * Check if the status represents a cancelled state
     */
    public boolean isCancelled() {
        return this == CANCELLED_BY_PATIENT ||
                this == CANCELLED_BY_CLINIC;
    }

    /**
     * Check if the status allows modification
     */
    public boolean isModifiable() {
        return this == REQUESTED ||
                this == CONFIRMED ||
                this == PAYMENT_PENDING;
    }
}