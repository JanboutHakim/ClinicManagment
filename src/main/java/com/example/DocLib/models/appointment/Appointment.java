package com.example.DocLib.models.appointment;

import com.example.DocLib.enums.AppointmentStatus;
import com.example.DocLib.models.doctor.Doctor;
import com.example.DocLib.models.patient.Patient;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "appointment")
public class Appointment {

    @ManyToOne
    @JoinColumn(name="doctor_id")
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name="patient_id")
    private Patient patient;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String reason;
    private String notes;
    private String cancellationReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Enumerated
    private AppointmentStatus status;



}
