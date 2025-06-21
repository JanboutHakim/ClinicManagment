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
@Entity
@Table(name = "patient_drug")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDrug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Renamed from drugId for clarity

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private String drugName;
    private String dosage;
    private int frequency;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private DrugStatus drugStatus;

    @OneToMany(mappedBy = "patientDrug", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DrugAlarm> drugAlarms;
}
