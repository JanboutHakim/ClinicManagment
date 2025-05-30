package com.example.DocLib.models.patient;

import com.example.DocLib.enums.Gender;
import com.example.DocLib.enums.Roles;
import com.example.DocLib.exceptions.custom.ResourceNotFoundException;
import com.example.DocLib.models.Appointment;
import com.example.DocLib.models.InsuranceCompany;
import com.example.DocLib.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;
import java.util.function.Consumer;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

@Entity
@Table(name = "patient")
public class Patient  {


    @Id
    private Long id;  // No @GeneratedValue here

    @OneToOne
    @MapsId  // This tells Hibernate to use the User's PK as Doctor's PK
    @JoinColumn(name = "id") // Foreign key column and PK column in Doctor table
    private User user;


    @Column(name = "address")
    private String address;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient",orphanRemoval = true)
    private List<Analyse> analyses;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient",orphanRemoval = true)
    private List<PatientHistoryRecord> patientHistoryRecords;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient",orphanRemoval = true)
    private List<PatientDrug> drugs;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "patient")
    private List<InsuranceCompany> insuranceCompanies;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Measurement> measurements;

    // Analyse
    public void addAnalyse(Analyse analyse) {
        analyses.add(analyse);
        analyse.setPatient(this);
    }

    public void updateAnalyse(Long analyseId, Consumer<Analyse> updater) {
        Analyse analyse = analyses.stream()
                .filter(a -> a.getId().equals(analyseId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Analyse with ID " + analyseId + " not found"));
        updater.accept(analyse);
    }

    public void removeAnalyseById(Long analyseId) {
        Analyse analyse = analyses.stream()
                .filter(a -> a.getId().equals(analyseId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Analyse with ID " + analyseId + " not found"));
        analyses.remove(analyse);
        analyse.setPatient(null);
    }

    // Patient History Record
    public void addHistoryRecord(PatientHistoryRecord record) {
        patientHistoryRecords.add(record);
        record.setPatient(this);
    }

    public void updateHistoryRecord(Long recordId, Consumer<PatientHistoryRecord> updater) {
        PatientHistoryRecord record = patientHistoryRecords.stream()
                .filter(r -> r.getId().equals(recordId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("History record with ID " + recordId + " not found"));
        updater.accept(record);
    }

    public void removeHistoryRecordById(Long recordId) {
        PatientHistoryRecord record = patientHistoryRecords.stream()
                .filter(r -> r.getId().equals(recordId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("History record with ID " + recordId + " not found"));
        patientHistoryRecords.remove(record);
        record.setPatient(null);
    }

    // Drug
    public void addDrug(PatientDrug drug) {
        drugs.add(drug);
        drug.setPatient(this);
    }

    public void updateDrug(Long drugId, Consumer<PatientDrug> updater) {
        PatientDrug drug = drugs.stream()
                .filter(d -> d.getId().equals(drugId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Drug with ID " + drugId + " not found"));
        updater.accept(drug);
    }

    public void removeDrugById(Long drugId) {
        PatientDrug drug = drugs.stream()
                .filter(d -> d.getId().equals(drugId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Drug with ID " + drugId + " not found"));
        drugs.remove(drug);
        drug.setPatient(null);
    }

    // Measurement
    public void addMeasurement(Measurement measurement) {
        measurements.add(measurement);
        measurement.setPatient(this);
    }

    public void updateMeasurement(Long measurementId, Consumer<Measurement> updater) {
        Measurement measurement = measurements.stream()
                .filter(m -> m.getId().equals(measurementId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Measurement with ID " + measurementId + " not found"));
        updater.accept(measurement);
    }

    public void removeMeasurementById(Long measurementId) {
        Measurement measurement = measurements.stream()
                .filter(m -> m.getId().equals(measurementId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Measurement with ID " + measurementId + " not found"));
        measurements.remove(measurement);
        measurement.setPatient(null);
    }









}