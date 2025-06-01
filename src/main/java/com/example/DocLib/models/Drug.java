package com.example.DocLib.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "Drugs")
public class Drug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drug_id")
    private Long drugId;

    @Column(name = "Name")
    private String name;

    @Column(name = "scientific_name")
    private String scientificName;

    @Column(name = "medication_dosage")
    private String medicationDosage;

    @Column(name = "Pharmaceutical-form")
    private String pharmaceuticalForm;

    @Column(name = "Company")
    private String company;

}