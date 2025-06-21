package com.example.DocLib.models.patient;

import com.example.DocLib.models.patient.PatientDrug;
import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
public class DrugAlarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_drug_id") // refer to the primary key of PatientDrug
    private PatientDrug patientDrug;

    private String alarmMessage;
    private LocalTime alarmTime;
}
