package com.example.DocLib.controllers;

import com.example.DocLib.dto.InsuranceCompanyDto;
import com.example.DocLib.dto.doctor.DoctorDto;
import com.example.DocLib.dto.patient.*;
import com.example.DocLib.services.implementation.AuthServices;
import com.example.DocLib.services.implementation.PatientServicesImp;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.AccessDeniedException; // Add necessary import for AccessDeniedException

import java.util.List;
/**
 * REST controller responsible for handling patient-related operations,
 * such as updating medical history, measurements, insurance, drugs, and retrieving associated doctors.
 *
 * <p>All routes require the authenticated user to match the {@code patientId} in the path.
 * If the authenticated user ID does not match, an {@link AccessDeniedException} is thrown.</p>
 *
 * <p>Base path: {@code /patients}</p>
 * @author Janbout Hakim
 **/
@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientServicesImp patientServicesImp;

    public PatientController(PatientServicesImp patientServicesImp) {
        this.patientServicesImp = patientServicesImp;
    }

    /**
     * Deletes a measurement record for the authenticated patient.
     *
     * @param patientId     ID of the patient.
     * @param measurementId ID of the measurement to delete.
     * @return Updated patient information.
     * @throws AccessDeniedException if the authenticated user is not the owner of the record.
     */
    @DeleteMapping("/{patientId}/measurements/{measurementId}")
    public ResponseEntity<PatientDto> deleteMeasurement(@PathVariable Long patientId, @PathVariable Long measurementId) {
        checkAuthenticatedUser(patientId);
        PatientDto patientDto = patientServicesImp.deleteMeasurement(patientId, measurementId);
        return ResponseEntity.ok(patientDto);
    }

    private static void checkAuthenticatedUser(Long patientId) {
        if (!AuthServices.getCurrentUserId().equals(patientId)) {
            throw new AccessDeniedException("You are not allowed to update this user.");
        }
    }

    /**
     * Updates drug information for the authenticated patient.
     *
     * @param patientId       ID of the patient.
     * @param patientDrugDto  DTO containing updated drug information.
     * @return Updated patient information.
     * @throws AccessDeniedException if the authenticated user is not the owner of the record.
     */
    @PostMapping("/{patientId}/drugs")
    public ResponseEntity<PatientDto> updateDrug(@PathVariable Long patientId, @RequestBody @Valid PatientDrugDto patientDrugDto) {
        checkAuthenticatedUser(patientId);
        PatientDto patientDto = patientServicesImp.updateDrug(patientId, patientDrugDto);
        return ResponseEntity.ok(patientDto);
    }

    /**
     * Updates analysis records for the authenticated patient.
     *
     * @param patientId   ID of the patient.
     * @param analyseDto  DTO containing new analysis data.
     * @return Updated patient information.
     * @throws AccessDeniedException if the authenticated user is not the owner of the record.
     */
    @PostMapping("/{patientId}/analyses")
    public ResponseEntity<PatientDto> updateAnalyse(@PathVariable Long patientId, @RequestBody @Valid AnalyseDto analyseDto) {
        checkAuthenticatedUser(patientId);
        PatientDto patientDto = patientServicesImp.updateAnalyse(patientId, analyseDto);
        return ResponseEntity.ok(patientDto);
    }

    /**
     * Updates the insurance company associated with the authenticated patient.
     * (NOTE: This method is currently returning an empty PatientDto — implementation may be incomplete.)
     *
     * @param patientId            ID of the patient.
     * @param insuranceCompanyDto  DTO with new insurance company details.
     * @return Updated patient information (currently returns empty DTO).
     * @throws AccessDeniedException if the authenticated user is not the owner of the record.
     */
    @PostMapping("/{patientId}/insurance-company")
    public ResponseEntity<PatientDto> updateInsuranceCompany(@PathVariable Long patientId, @RequestBody @Valid InsuranceCompanyDto insuranceCompanyDto) {
        checkAuthenticatedUser(patientId);
        // Currently not implemented
        return ResponseEntity.ok(new PatientDto());
    }

    /**
     * Adds a new measurement record for the authenticated patient.
     *
     * @param patientId       ID of the patient.
     * @param measurementDto  DTO with measurement details.
     * @return Updated patient information.
     * @throws AccessDeniedException if the authenticated user is not the owner of the record.
     */
    @PostMapping("/{patientId}/measurements")
    public ResponseEntity<PatientDto> updateMeasurement(@PathVariable Long patientId, @RequestBody @Valid MeasurementDto measurementDto) {
        checkAuthenticatedUser(patientId);
        PatientDto patientDto = patientServicesImp.updateMeasurement(patientId, measurementDto);
        return ResponseEntity.ok(patientDto);
    }

    /**
     * Updates the patient's history record.
     *
     * @param patientId   ID of the patient.
     * @param historyDto  DTO with updated history record.
     * @return Updated patient information.
     * @throws AccessDeniedException if the authenticated user is not the owner of the record.
     */
    @PostMapping("/{patientId}/history")
    public ResponseEntity<PatientDto> updateHistoryRecord(@PathVariable Long patientId, @RequestBody @Valid PatientHistoryRecordDto historyDto) {
        checkAuthenticatedUser(patientId);
        PatientDto patientDto = patientServicesImp.updateHistoryRecord(patientId, historyDto);
        return ResponseEntity.ok(patientDto);
    }

    /**
     * Retrieves a list of doctors associated with the authenticated patient.
     *
     * @param patientId ID of the patient.
     * @return List of doctors.
     * @throws AccessDeniedException if the authenticated user is not the owner of the record.
     */
    @GetMapping("/{patientId}/doctors")
    public ResponseEntity<List<DoctorDto>> getPatientDoctors(@PathVariable Long patientId) {
        checkAuthenticatedUser(patientId);
        List<DoctorDto> doctorDto = patientServicesImp.getPatientDoctors(patientId);
        return ResponseEntity.ok(doctorDto);
    }
}
