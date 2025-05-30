package com.example.DocLib.models.patient;

import com.example.DocLib.enums.DrugStatus;
import com.example.DocLib.exceptions.custom.ResourceNotFoundException;
import com.example.DocLib.models.Drug;
import com.example.DocLib.models.compostionKey.PatientDrugId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

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

    private String drugName;

    private String Dosage;

    private boolean isNew;

    private int Frequency;

    private LocalDate startDate ;

    private LocalDate endDate ;

    @Enumerated(EnumType.STRING)
    private DrugStatus drugStatus;

    @OneToMany(mappedBy = "patientDrug", cascade = CascadeType.ALL)
    private List<DrugAlarm> drugAlarms;



}
