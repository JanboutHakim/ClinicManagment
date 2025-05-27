package com.example.DocLib.models.patient;

import com.example.DocLib.enums.DrugStatus;
import com.example.DocLib.models.Drug;
import com.example.DocLib.models.compostionKey.PatientDrugId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patient_drug")
public class PatientDrug {

    @EmbeddedId
    private PatientDrugId id;

    @ManyToOne
    @MapsId("patientId")
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @MapsId("drugId")
    @JoinColumn(name = "drug_id")
    private Drug drug;

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

    private boolean isNew;

    @Enumerated(EnumType.STRING)
    private DrugStatus drugStatus;

    @OneToMany(mappedBy = "patientDrug", cascade = CascadeType.ALL)
    private List<DrugAlarm> drugAlarms;
}
