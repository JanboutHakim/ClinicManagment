package com.example.DocLib.controllers;

import com.example.DocLib.dto.InsuranceCompanyDto;
import com.example.DocLib.dto.patient.*;
import com.example.DocLib.services.implementation.PatientServicesImp;
import jakarta.validation.Valid;
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

    /**
     * Retrieves all the patients from the system.
     *
     * @return List of PatientDto objects representing all patients in the system.
     */
    @GetMapping("/all")
    public List<PatientDto> getAllPatients() {
        return patientServicesImp.getAllPatients();
    }

    /**
     * Retrieves a specific patient by ID.
     *
     * @param id the ID of the patient to retrieve
     * @return ResponseEntity containing the PatientDto object representing the patient
     */
    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatient(@PathVariable Long id) {
        PatientDto patientDto = patientServicesImp.getPatient(id);
        return ResponseEntity.ok(patientDto);
    }


    /**
     * Adds a new measurement for a patient with the specified ID.
     *
     * @param id the ID of the patient
     * @param measurementDto the MeasurementDto object containing the measurement details
     * @return ResponseEntity containing a list of MeasurementDto objects after adding the new measurement
     */
    @PostMapping("/{id}/addMeasurement")
    public ResponseEntity<PatientDto> addMeasurement(@PathVariable Long id, @RequestBody @Valid MeasurementDto measurementDto) {
        PatientDto patientDto = patientServicesImp.addMeasurement(id, measurementDto);
        return ResponseEntity.ok(patientDto);
    }

    /**
     * Deletes a measurement for a specific patient.
     *
     * @param id the ID of the patient
     * @param measurementId the MeasurementDto object representing the measurement to be deleted
     * @return a ResponseEntity containing a list of MeasurementDto objects after deletion
     */
    @PostMapping("/{id}/Measurement/delete/{measurementId}")
    public ResponseEntity<PatientDto> deleteMeasurement(@PathVariable Long id, @PathVariable Long measurementId) {
        PatientDto patientDto = patientServicesImp.deleteMeasurement(id, measurementId);
        return ResponseEntity.ok(patientDto);
    }

    /**
     * Adds a new drug to a patient's profile.
     *
     * @param id The ID of the patient.
     * @param patientDrugDto The DTO representing the drug to be added.
     * @return ResponseEntity containing the updated PatientDto with the newly added drug.
     */
    @PostMapping("/{id}/addDrug")
    public ResponseEntity<PatientDto> addDrug(@PathVariable Long id, @RequestBody @Valid PatientDrugDto patientDrugDto) {
        PatientDto patientDto = patientServicesImp.addDrug(id, patientDrugDto);
        return ResponseEntity.ok(patientDto);
    }

    /**
     * Deletes a drug associated with a patient.
     *
     * @param id The ID of the patient.
     * @param patientDrugId The PatientDrugDto object representing the drug to be deleted.
     * @return ResponseEntity<PatientDto> The updated PatientDto object after deleting the drug.
     */
    @PostMapping("/{id}/Drug/delete/{patientDrugId}")
    public ResponseEntity<PatientDto> deleteDrug(@PathVariable Long id, @PathVariable Long patientDrugId) {
        PatientDto patientDto = patientServicesImp.deleteDrug(id, patientDrugId);
        return ResponseEntity.ok(patientDto);
    }

    /**
     *
     */
    @PostMapping("/{id}/setDrugAlarm")
    public ResponseEntity<PatientDto> setDrugAlarm(@PathVariable Long id, @RequestBody @Valid PatientDrugDto patientDrugDto, @RequestBody List<DrugAlarmDto> drugAlarmDtos) {
        PatientDto updatedPatientDto = patientServicesImp.setDrugAlarm(patientDrugDto, drugAlarmDtos, id);
        return ResponseEntity.ok(updatedPatientDto);
    }

    /**
     * Adds a new analysis to a patient.
     *
     * @param id The ID of the patient to add the analysis to
     * @param analyseDto The analysis information to be added
     * @return ResponseEntity containing the updated PatientDto with the added analysis
     */
    @PostMapping("/{id}/addAnalyse")
    public ResponseEntity<PatientDto> addAnalyse(@PathVariable Long id, @RequestBody @Valid AnalyseDto analyseDto) {
        PatientDto patientDto = patientServicesImp.addAnalyse(id, analyseDto);
        return ResponseEntity.ok(patientDto);
    }
    /**
     * Adds an insurance company to the patient with the specified ID.
     *
     * @param id The ID of the patient to add insurance company to.
     * @param insuranceCompanyDto The InsuranceCompanyDto object containing information of the insurance company to be added.
     * @return ResponseEntity with the updated PatientDto after adding the insurance company.
     */
    @PostMapping("/{id}/addInsuranceCompany")
    public ResponseEntity<PatientDto> addInsuranceCompany(@PathVariable Long id, @RequestBody @Valid InsuranceCompanyDto insuranceCompanyDto) {
        PatientDto patientDto = patientServicesImp.addInsuranceCompany(id, insuranceCompanyDto);
        return ResponseEntity.ok(patientDto);
    }

    /**
     * Delete an analysis for a patient.
     *
     * @param id The ID of the patient to delete the analysis from.
     * @param patientAnalyseId The analysis to be deleted.
     * @return ResponseEntity with the updated PatientDto after deletion.
     */
    @PostMapping("/{id}/Analyse/delete/{patientAnalyseId}")
    public ResponseEntity<PatientDto> deleteAnalyse(@PathVariable Long id, @PathVariable Long patientAnalyseId) {
        PatientDto patientDto = patientServicesImp.deleteAnalyse(id, patientAnalyseId);
        return ResponseEntity.ok(patientDto);
    }
    /**
     * Deletes an insurance company from a patient's list of insurance companies.
     *
     * @param id The ID of the patient from which the insurance company will be deleted
     * @param insuranceCompanyDto The InsuranceCompanyDto object representing the insurance company to be deleted
     * @return ResponseEntity containing the updated PatientDto object after deletion
     */
    @PostMapping("/{id}/deleteInsuranceCompany")
    public ResponseEntity<PatientDto> deleteInsuranceCompany(@PathVariable Long id, @RequestBody @Valid InsuranceCompanyDto insuranceCompanyDto) {
        PatientDto patientDto = patientServicesImp.deleteInsuranceCompany(id, insuranceCompanyDto);
        return ResponseEntity.ok(patientDto);
    }

    /**
     * Retrieves all measurements for a given patient ID.
     *
     * @param id the ID of the patient
     * @return a ResponseEntity containing a list of MeasurementDto objects representing the measurements of the patient
     */
    @PostMapping("/{id}/getAllMeasurement")
    public ResponseEntity<List<MeasurementDto>> getAllMeasurement(@PathVariable Long id) {
        List<MeasurementDto> measurementDtos = patientServicesImp.getAllMeasurement(id);
        return ResponseEntity.ok(measurementDtos);
    }




}
