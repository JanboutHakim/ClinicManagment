package com.example.DocLib.controllers;

import com.example.DocLib.dto.patient.MeasurementDto;
import com.example.DocLib.dto.patient.PatientDrugDto;
import com.example.DocLib.dto.patient.PatientDto;
import com.example.DocLib.services.PatientServicesImp;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {
    private final PatientServicesImp patientServicesImp;


    public PatientController(PatientServicesImp patientServicesImp) {
        this.patientServicesImp = patientServicesImp;
    }

    @GetMapping("/all")
    public List<PatientDto> getAllPatients() {
        return patientServicesImp.getAllPatients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatient(@PathVariable Long id) {
        PatientDto patientDto = patientServicesImp.getPatient(id);
        return ResponseEntity.ok(patientDto);
    }


    @PostMapping("/{id}/addMeasurement")
    public ResponseEntity<List<MeasurementDto>> addMeasurement(@PathVariable Long id, @RequestBody @Valid MeasurementDto measurementDto) {
        List<MeasurementDto> measurementDtos = patientServicesImp.addMeasurement(id, measurementDto);
        return ResponseEntity.ok(measurementDtos);
    }

    @PostMapping("/{id}/deleteMeasurement")
    public ResponseEntity<List<MeasurementDto>> deleteMeasurement(@PathVariable Long id, @RequestBody @Valid MeasurementDto measurementDto) {
        List<MeasurementDto> measurementDtos = patientServicesImp.deleteMeasurement(id, measurementDto);
        return ResponseEntity.ok(measurementDtos);
    }

    @PostMapping("/{id}/addDrug")
    public ResponseEntity<PatientDto> addDrug(@PathVariable Long id, @RequestBody @Valid PatientDrugDto patientDrugDto) {
        PatientDto patientDto = patientServicesImp.addDrug(id, patientDrugDto);
        return ResponseEntity.ok(patientDto);
    }

    @PostMapping("/{id}/deleteDrug")
    public ResponseEntity<PatientDto> deleteDrug(@PathVariable Long id, @RequestBody @Valid PatientDrugDto patientDrugDto) {
        PatientDto patientDto = patientServicesImp.deleteDrug(id, patientDrugDto);
        return ResponseEntity.ok(patientDto);
    }


}
