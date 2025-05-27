package com.example.DocLib.models.patient;

import com.example.DocLib.enums.Gender;
import com.example.DocLib.enums.Roles;
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient")
    private List<Analyse> analyses;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient")
    private List<PatientHistoryRecord> patientHistoryRecords;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient")
    private List<PatientDrug> drugs;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "patient")
    private List<InsuranceCompany> insuranceCompanies;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Measurement> measurements;







}