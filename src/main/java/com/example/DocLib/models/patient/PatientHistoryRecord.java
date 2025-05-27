package com.example.DocLib.models.patient;
import com.example.DocLib.enums.HistoryRecordTypes;
import jakarta.persistence.*;

import java.util.Date;

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
    private Date date;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}