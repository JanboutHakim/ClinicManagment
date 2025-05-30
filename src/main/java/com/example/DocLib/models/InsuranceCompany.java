package com.example.DocLib.models;

import com.example.DocLib.enums.InsuranceCompanies;
import com.example.DocLib.models.doctor.Doctor;
import com.example.DocLib.models.patient.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "insurance_company")
public class InsuranceCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long insuranceId;

    private InsuranceCompanies companyName;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="patient_id", nullable=false)
    private Patient patient;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="doctor_id", nullable=false)
    private Doctor doctor;


}
