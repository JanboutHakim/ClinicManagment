package com.example.DocLib.models.patient;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
public class DrugAlarm {
    @Id
    private Long id;  // add explicit primary key

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "patient_id", referencedColumnName = "patient_id"),
            @JoinColumn(name = "drug_id", referencedColumnName = "drug_id")
    })
    private PatientDrug patientDrug;

    private String alarmMessage;
    private LocalTime alarmTime;
}
