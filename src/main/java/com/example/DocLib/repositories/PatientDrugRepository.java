package com.example.DocLib.repositories;

import com.example.DocLib.models.patient.PatientDrug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientDrugRepository extends JpaRepository<PatientDrug,Long> {
    List<PatientDrug> findByPatientId(Long patientID);
}
