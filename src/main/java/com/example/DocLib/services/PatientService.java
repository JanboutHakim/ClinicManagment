package com.example.DocLib.services;

import com.example.DocLib.dto.*;
import com.example.DocLib.dto.patient.*;
import com.example.DocLib.models.patient.Measurement;
import java.util.List;

public interface PatientService {
    List<PatientDto> getAllPatients();
    PatientDto getPatient(Long id);
    PatientDto addDrug(Long id, PatientDrugDto patientDrugDto);
    PatientDto addAnalyse(Long id, AnalyseDto analyseDto);
    PatientDto addInsuranceCompany(Long id, InsuranceCompanyDto insuranceCompanyDto);
    PatientDto deleteDrug(Long id, PatientDrugDto patientDrugDto);
    PatientDto deleteAnalyse(Long id, AnalyseDto analyseDto);
    PatientDto deleteInsuranceCompany(Long id, InsuranceCompanyDto insuranceCompanyDto);
    PatientDrugDto setDrugAlarm(PatientDrugDto patientDrugDto, List<DrugAlarmDto> paDrugAlarmDto, Long id);
    List<MeasurementDto> getAllMeasurement(Long id);
    List<MeasurementDto> addMeasurement(Long id, MeasurementDto measurement);
    List<MeasurementDto> deleteMeasurement(Long id, MeasurementDto measurement);
}
