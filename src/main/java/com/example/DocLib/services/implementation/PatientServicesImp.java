package com.example.DocLib.services.implementation;

import com.example.DocLib.dto.*;
import com.example.DocLib.dto.patient.*;
import com.example.DocLib.exceptions.custom.ResourceNotFoundException;
import com.example.DocLib.models.*;
import com.example.DocLib.models.patient.*;
import com.example.DocLib.repositories.PatientRepository;
import com.example.DocLib.services.interfaces.PatientServices;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServicesImp implements PatientServices {

    private final PatientRepository patientRepository;
    private final DrugServicesImp drugServiceImp;
    private final ModelMapper modelMapper;


    @Autowired
    public PatientServicesImp(PatientRepository patientRepository, DrugServicesImp drugServiceImp, ModelMapper modelMapper) {
        this.patientRepository = patientRepository;
        this.drugServiceImp = drugServiceImp;
        this.modelMapper = modelMapper;

    }

    @Override
    public List<PatientDto> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(patient -> modelMapper.map(patient, PatientDto.class))
                .collect(Collectors.toList());
    }
    @Override
    public PatientDto getPatientById(Long id){
        return patientRepository.findById(id)
                .map(patient -> modelMapper.map(patient,PatientDto.class))
                .orElseThrow(() -> new EntityNotFoundException("Patient with id " + id + " not found"));
    }

    @Override
    @Transactional
    public PatientDto addDrug(Long patientId, PatientDrugDto patientDrugDto) {
        Patient patient = getPatientEntity(patientId);
        if(patientDrugDto.isNew())
             addNewDrug(patientId,patientDrugDto);
        else {
            patientDrugDto.setDrugName(drugServiceImp.getDrugByID(patientDrugDto.getDrugId()).getName());
            patient.addDrug(modelMapper.map(patientDrugDto, PatientDrug.class));
        }

        return convertPatientToDto(patient);
    }

    private void addNewDrug(Long patientId, PatientDrugDto patientDrugDto) {

    }

    @Override
    @Transactional
    public PatientDto deleteDrug(Long id, Long patientDrugId) {
        Patient patient = getPatientEntity(id);
        patient.removeDrugById(patientDrugId);
        return convertPatientToDto(patient);
    }

    @Override
    @Transactional
    public PatientDto addInsuranceCompany(Long patientId, InsuranceCompanyDto insuranceCompanyDto){
        Patient patient = getPatientEntity(patientId);
        patient.getInsuranceCompanies().add(modelMapper.map(insuranceCompanyDto, InsuranceCompany.class));
        return convertPatientToDto(patient);
    }

    @Override
    public PatientDto deleteInsuranceCompany(Long id, InsuranceCompanyDto dto) {
        return null;
    }

    @Override
    @Transactional  // This annotation is important, it makes the method transactional
    public PatientDto setDrugAlarm(PatientDrugDto patientDrugDto, List<DrugAlarmDto> paDrugAlarmDto, Long patientId){
        Patient patient = getPatientEntity(patientId);
        PatientDrug patientDrug = modelMapper.map(patientDrugDto, PatientDrug.class);
        List<PatientDrug> patientDrugList = patient.getDrugs();
        for(PatientDrug patientDrug1: patientDrugList){
            if(patientDrug1.getId().equals(patientDrug.getId())){
                patientDrug1.setDrugAlarms(
                        paDrugAlarmDto.stream()
                                .map(patientDrugAlarmDto -> modelMapper.map(patientDrugAlarmDto, DrugAlarm.class))
                                .collect(Collectors.toList())
                );
                patientRepository.save(patient);  // Persist changes
                return modelMapper.map(patient, PatientDto.class);  // Return updated PatientDrugDto
            }
        }
        // If we haven't found the matching drug, throw an exception or return a default value.
        throw new ResourceNotFoundException("Drug with id " + patientDrugDto.getId() + " not found");
    }

    @Override
    @Transactional
    public PatientDto addAnalyse(Long id, AnalyseDto analyseDto) {
        Patient patient = getPatientEntity(id);
        patient.addAnalyse(modelMapper.map(analyseDto,Analyse.class));
        return convertPatientToDto(patient);
    }

    @Override
    @Transactional
    public PatientDto deleteAnalyse(Long id, Long analyseId) {
        Patient patient = getPatientEntity(id);
        patient.removeAnalyseById(analyseId);
        return convertPatientToDto(patient);
    }

    @Override
    @Transactional
    public PatientDto updateAnalyse(Long PatientId, AnalyseDto dto) {
        Patient patient = getPatientEntity(PatientId);
        patient.updateAnalyse(dto.getPatientId(),anl -> modelMapper.map(dto,anl));
        return convertPatientToDto(patient);
    }

    @Override
    public List<MeasurementDto> getAllMeasurement(Long patientId){
        Patient patient = getPatientEntity(patientId);
        return patient.getMeasurements()
                .stream()
                .map(Measurement -> modelMapper.map(Measurement,MeasurementDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PatientDto addMeasurement(Long PatientId, MeasurementDto measurement) {
        Patient patient = getPatientEntity(PatientId);
        patient.addMeasurement(modelMapper.map(measurement,Measurement.class));
        return convertPatientToDto(patient);
    }

    @Override
    @Transactional
    public PatientDto deleteMeasurement(Long PatientId, Long measurementId) {
        Patient patient = getPatientEntity(PatientId);
        patient.removeMeasurementById(measurementId);
        return convertPatientToDto(patient);
    }

    @Override
    @Transactional
    public PatientDto updateMeasurement(Long patientId, MeasurementDto dto) {
        Patient patient = getPatientEntity(patientId);
        patient.updateMeasurement(dto.getId(),mes -> modelMapper.map(dto,mes));
        return convertPatientToDto(patient);
    }


    @Override
    @Transactional
    public PatientDto updateDrug(Long patientId, PatientDrugDto dto) {
        Patient patient = getPatientEntity(patientId);
        patient.updateDrug(dto.getId(),drg -> modelMapper.map(dto,drg));
        return convertPatientToDto(patient);

    }

    @Override
    @Transactional
    public List<PatientHistoryRecordDto> getAllHistoryRecords(Long patientId) {
        Patient patient = getPatientEntity(patientId);
        return patient.getPatientHistoryRecords()
                .stream()
                .map(historyRecord -> modelMapper.map(historyRecord, PatientHistoryRecordDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PatientDto addHistoryRecord(Long patientId, PatientHistoryRecordDto historyDto) {
        Patient patient = getPatientEntity(patientId);
        patient.addHistoryRecord(modelMapper.map(historyDto, PatientHistoryRecord.class));
        return convertPatientToDto(patient);
    }

    @Override
    public PatientDto deleteHistoryRecord(Long PatientId, Long historyRecordId) {
        Patient patient = getPatientEntity(PatientId);
        patient.removeHistoryRecordById(historyRecordId);
        return convertPatientToDto(patient);
    }

    @Override
    public PatientDto updateHistoryRecord(Long id, PatientHistoryRecordDto historyDto) {
        return null;
    }

    public Patient getPatientEntity(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID " + id + " not found"));
    }
    private PatientDto convertPatientToDto(Patient patient){
        return modelMapper.map(patient,PatientDto.class);
    }

}


