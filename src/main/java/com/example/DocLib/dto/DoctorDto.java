package com.example.DocLib.dto;

import com.example.DocLib.models.Appointment;
import com.example.DocLib.models.InsuranceCompany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DoctorDto {

    private Long id;

    private String address;

    private String specialization;

    private int yearsOfExperience;

    private String unionMembershipNumber;

    private List<Appointment> appointments;

    private List<InsuranceCompany> insuranceCompanies;
}