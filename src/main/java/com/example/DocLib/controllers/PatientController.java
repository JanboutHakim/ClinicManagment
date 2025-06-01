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
     * Retrieves a list of all patients.
     *
     * @return a List of PatientDto objects representing all patients in the system
     */
    @GetMapping("/all")
    public List<PatientDto> getAllPatients() {
        return patientServicesImp.getAllPatients();
    }

    /**
     * Retrieve a specific patient by their ID.
     *
     * @param id The ID of the patient to retrieve.
     * @return ResponseEntity<PatientDto> The ResponseEntity containing the PatientDto object.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatient(@PathVariable Long id) {
        PatientDto patientDto = patientServicesImp.getPatientById(id);
        return ResponseEntity.ok(patientDto);
    }


    /**
     * Adds a new measurement for a specified patient.
     *
     * @param id The ID of the patient to add the measurement for.
     * @param measurementDto The MeasurementDto object containing the measurement details.
     * @return ResponseEntity with the updated PatientDto after adding the measurement.
     */
    @PostMapping("/{id}/Measurement")
    public ResponseEntity<PatientDto> addMeasurement(@PathVariable Long id, @RequestBody @Valid MeasurementDto measurementDto) {
        PatientDto patientDto = patientServicesImp.addMeasurement(id, measurementDto);
        return ResponseEntity.ok(patientDto);
    }

    /**
     * Deletes a measurement for a specific patient.
     *
     * @param id The ID of the patient whose measurement is to be deleted
     * @param measurementId The ID of the measurement to be deleted
     * @return ResponseEntity with the updated PatientDto after deleting the measurement
     */
    @DeleteMapping("/{id}/Measurement/{measurementId}")
    public ResponseEntity<PatientDto> deleteMeasurement(@PathVariable Long id, @PathVariable Long measurementId) {
        PatientDto patientDto = patientServicesImp.deleteMeasurement(id, measurementId);
        return ResponseEntity.ok(patientDto);
    }

    /**
     * Adds a drug to a patient's record.
     *
     * @param id The ID of the patient.
     * @param patientDrugDto The DTO object representing the drug to add.
     * @return ResponseEntity containing the updated PatientDto after adding the drug.
     */
    @PostMapping("/{id}/Drug")
    public ResponseEntity<PatientDto> addDrug(@PathVariable Long id, @RequestBody @Valid PatientDrugDto patientDrugDto) {
        PatientDto patientDto = patientServicesImp.addDrug(id, patientDrugDto);
        return ResponseEntity.ok(patientDto);
    }

    /**
     * Deletes a drug for a patient.
     *
     * @param id The ID of the patient.
     * @param patientDrugId The ID of the drug to be deleted for the patient.
     * @return ResponseEntity containing the updated PatientDto after deleting the drug.
     */
    @DeleteMapping("/{id}/Drug/{patientDrugId}")
    public ResponseEntity<PatientDto> deleteDrug(@PathVariable Long id, @PathVariable Long patientDrugId) {
        PatientDto patientDto = patientServicesImp.deleteDrug(id, patientDrugId);
        return ResponseEntity.ok(patientDto);
    }

    /**
     * Sets drug alarms for a given patient.
     *
     * @param id The ID of the patient.
     * @param patientDrugDto The data transfer object representing the patient drug details.
     * @param drugAlarmDtos A list of drug alarm data transfer objects to set for the patient drug.
     *
     * @return ResponseEntity containing the updated PatientDto after setting the drug alarms.
     */
    @PostMapping("/{id}/setDrugAlarm")
    public ResponseEntity<PatientDto> setDrugAlarm(@PathVariable Long id, @RequestBody @Valid PatientDrugDto patientDrugDto, @RequestBody List<DrugAlarmDto> drugAlarmDtos) {
        PatientDto updatedPatientDto = patientServicesImp.setDrugAlarm(patientDrugDto, drugAlarmDtos, id);
        return ResponseEntity.ok(updatedPatientDto);
    }

    /**
     * Adds a new analysis to the patient with the given ID.
     *
     * @param id The ID of the patient to add the analysis to.
     * @param analyseDto The AnalyseDto object containing the details of the analysis to be added.
     * @return ResponseEntity containing the updated PatientDto after adding the analysis.
     */
    @PostMapping("/{id}/Analyse")
    public ResponseEntity<PatientDto> addAnalyse(@PathVariable Long id, @RequestBody @Valid AnalyseDto analyseDto) {
        PatientDto patientDto = patientServicesImp.addAnalyse(id, analyseDto);
        return ResponseEntity.ok(patientDto);
    }
    /**
     * Adds an insurance company to the patient identified by the given ID.
     *
     * @param id The ID of the patient to whom the insurance company is being added.
     * @param insuranceCompanyDto The DTO representing the insurance company to add.
     * @return ResponseEntity with the updated PatientDto after adding the insurance company.
     */
    @PostMapping("/{id}/InsuranceCompany")
    public ResponseEntity<PatientDto> addInsuranceCompany(@PathVariable Long id, @RequestBody @Valid InsuranceCompanyDto insuranceCompanyDto) {
        PatientDto patientDto = patientServicesImp.addInsuranceCompany(id, insuranceCompanyDto);
        return ResponseEntity.ok(patientDto);
    }

    /**
     * Deletes an analysis for a specific patient.
     *
     * @param id The ID of the patient.
     * @param patientAnalyseId The ID of the analysis to be deleted.
     * @return ResponseEntity with the updated PatientDto after deleting the analysis.
     */
    @DeleteMapping("/{id}/Analyse/{patientAnalyseId}")
    public ResponseEntity<PatientDto> deleteAnalyse(@PathVariable Long id, @PathVariable Long patientAnalyseId) {
        PatientDto patientDto = patientServicesImp.deleteAnalyse(id, patientAnalyseId);
        return ResponseEntity.ok(patientDto);
    }
    /**
     * Deletes an insurance company from a patient's list of insurance companies based on the provided ID.
     *
     * @param id The ID of the patient from which the insurance company should be deleted.
     * @param insuranceCompanyDto The InsuranceCompanyDto object representing the insurance company to be deleted.
     * @return ResponseEntity containing the updated PatientDto after deleting the insurance company.
     */
    @DeleteMapping("/{id}/deleteInsuranceCompany")
    public ResponseEntity<PatientDto> deleteInsuranceCompany(@PathVariable Long id, @RequestBody @Valid InsuranceCompanyDto insuranceCompanyDto) {
        PatientDto patientDto = patientServicesImp.deleteInsuranceCompany(id, insuranceCompanyDto);
        return ResponseEntity.ok(patientDto);
    }

    /**
     * Retrieves all measurements for a specific patient.
     *
     * @param id The unique identifier of the patient.
     * @return A ResponseEntity containing a list of MeasurementDto objects representing all measurements of the specified patient.
     */
    @PostMapping("/{id}/getAllMeasurement")
    public ResponseEntity<List<MeasurementDto>> getAllMeasurement(@PathVariable Long id) {
        List<MeasurementDto> measurementDtos = patientServicesImp.getAllMeasurement(id);
        return ResponseEntity.ok(measurementDtos);
    }

    /**
     * Updates a drug for a specific patient.
     *
     * @param id The ID of the patient.
     * @param patientDrugDto The DTO containing information about the drug to be updated.
     * @return ResponseEntity containing the updated PatientDto.
     */
    @PutMapping("/{id}/updateDrug")
    public ResponseEntity<PatientDto> updateDrug(@PathVariable Long id, @RequestBody @Valid PatientDrugDto patientDrugDto) {
        PatientDto patientDto = patientServicesImp.updateDrug(id, patientDrugDto);
        return ResponseEntity.ok(patientDto);
    }

    /**
     * Updates the analysis for a patient.
     *
     * @param id The ID of the patient.
     * @param analyseDto The AnalyseDto object containing the updated analysis information.
     * @return ResponseEntity with the updated PatientDto object.
     */
    @PutMapping("/{id}/updateAnalyse")
    public ResponseEntity<PatientDto> updateAnalyse(@PathVariable Long id, @RequestBody @Valid AnalyseDto analyseDto) {
        PatientDto patientDto = patientServicesImp.updateAnalyse(id, analyseDto);
        return ResponseEntity.ok(patientDto);
    }

    /**
     * Updates the measurement for a specific patient.
     *
     * @param id The ID of the patient whose measurement is being updated.
     * @param measurementDto The MeasurementDto object containing the updated measurement data.
     * @return ResponseEntity with the updated PatientDto object.
     */
    @PutMapping("/{id}/updateMeasurement")
    public ResponseEntity<PatientDto> updateMeasurement(@PathVariable Long id, @RequestBody @Valid MeasurementDto measurementDto) {
        PatientDto patientDto = patientServicesImp.updateMeasurement(id, measurementDto);
        return ResponseEntity.ok(patientDto);
    }

    /**
     * Retrieves all history records for a specific patient.
     *
     * @param id The ID of the patient for whom history records need to be retrieved.
     * @return A ResponseEntity with a list of PatientHistoryRecordDto objects representing the history records of the patient.
     */
    @GetMapping("/{id}/history")
    public ResponseEntity<List<PatientHistoryRecordDto>> getAllHistoryRecords(@PathVariable Long id) {
        List<PatientHistoryRecordDto> records = patientServicesImp.getAllHistoryRecords(id);
        return ResponseEntity.ok(records);
    }

    /**
     * Add a history record to a patient's history.
     *
     * @param id the unique identifier of the patient
     * @param historyDto the DTO containing information of the history record to be added
     * @return ResponseEntity containing the updated PatientDto object after adding the history record
     */
    @PostMapping("/{id}/addHistory")
    public ResponseEntity<PatientDto> addHistoryRecord(@PathVariable Long id, @RequestBody @Valid PatientHistoryRecordDto historyDto) {
        PatientDto patientDto = patientServicesImp.addHistoryRecord(id, historyDto);
        return ResponseEntity.ok(patientDto);
    }

    /**
     * Deletes a history record for a specific patient.
     *
     * @param id The ID of the patient.
     * @param historyRecordId The ID of the history record to be deleted.
     * @return ResponseEntity containing the updated PatientDto after deleting the history record.
     */
    @DeleteMapping("/{id}/deleteHistory/{historyRecordId}")
    public ResponseEntity<PatientDto> deleteHistoryRecord(@PathVariable Long id, @PathVariable Long historyRecordId) {
        PatientDto patientDto = patientServicesImp.deleteHistoryRecord(id, historyRecordId);
        return ResponseEntity.ok(patientDto);
    }

    /**
     *
     */
    @PutMapping("/{id}/updateHistory")
    public ResponseEntity<PatientDto> updateHistoryRecord(@PathVariable Long id, @RequestBody @Valid PatientHistoryRecordDto historyDto) {
        PatientDto patientDto = patientServicesImp.updateHistoryRecord(id, historyDto);
        return ResponseEntity.ok(patientDto);
    }






}
