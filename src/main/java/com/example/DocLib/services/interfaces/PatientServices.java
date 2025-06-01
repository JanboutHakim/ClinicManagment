package com.example.DocLib.services.interfaces;

import com.example.DocLib.dto.*;
import com.example.DocLib.dto.patient.*;

import java.util.List;


public interface PatientServices {

        // Core
        List<PatientDto> getAllPatients();
        PatientDto getPatientById(Long id);

        // Drug
        PatientDto addDrug(Long id, PatientDrugDto patientDrugDto);
        PatientDto deleteDrug(Long id, Long patientDrugId);
        PatientDto updateDrug(Long id, PatientDrugDto patientDrugDto);
        PatientDto setDrugAlarm(PatientDrugDto patientDrugDto, List<DrugAlarmDto> alarms, Long id);

        // Analyse
        PatientDto addAnalyse(Long id, AnalyseDto analyseDto);
        PatientDto deleteAnalyse(Long id, Long analyseId);
        PatientDto updateAnalyse(Long id, AnalyseDto analyseDto);

        // Insurance
        PatientDto addInsuranceCompany(Long id, InsuranceCompanyDto dto);
        PatientDto deleteInsuranceCompany(Long id, InsuranceCompanyDto dto);

        // Measurement
        List<MeasurementDto> getAllMeasurement(Long id);
        PatientDto addMeasurement(Long id, MeasurementDto measurement);
        PatientDto deleteMeasurement(Long id, Long measurementId);
        PatientDto updateMeasurement(Long id, MeasurementDto measurementDto);

        // History Records
        List<PatientHistoryRecordDto> getAllHistoryRecords(Long id);
        PatientDto addHistoryRecord(Long id, PatientHistoryRecordDto historyDto);
        PatientDto deleteHistoryRecord(Long id, Long historyRecordId);
        PatientDto updateHistoryRecord(Long id, PatientHistoryRecordDto historyDto);

    }


