package com.example.DocLib.models.patient;
import com.example.DocLib.enums.HistoryRecordTypes;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class PatientHistoryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private HistoryRecordTypes historyRecordType;

    @Column(name = "record_detail")
    private String recordDetail;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}