package com.example.DocLib.models;

import com.example.DocLib.enums.Gender;
import com.example.DocLib.enums.Roles;
import com.example.DocLib.models.patient.Measurement;
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
@Table(name = "doctor")
public class Doctor  {

    @Id
    private Long id;  // No @GeneratedValue here

    @OneToOne
    @MapsId  // This tells Hibernate to use the User's PK as Doctor's PK
    @JoinColumn(name = "id") // Foreign key column and PK column in Doctor table
    private User user;

    private String address;

    private String specialization;

    private int yearsOfExperience;

    @Column(nullable = false)
    private String unionMembershipNumber;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "doctor")
    private List<Experience> experiences;

    @OneToMany(mappedBy = "doctor")
    private List<ScientificBackground> scientificBackgrounds;

    @OneToMany(mappedBy = "doctor")
    private List<Service> services;

    @OneToMany(mappedBy = "doctor")
    private List<DoctorSchedule> patients;

    @OneToMany(mappedBy = "doctor")
    private List<HolidaySchedule> holidaySchedules;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InsuranceCompany> insuranceCompanies;

}
