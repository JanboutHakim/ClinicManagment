package com.example.DocLib.services;

import com.example.DocLib.dto.*;
import com.example.DocLib.dto.patient.*;
import com.example.DocLib.models.*;
import com.example.DocLib.models.patient.*;
import com.example.DocLib.repositories.PatientRepository;
import com.example.DocLib.repositories.UserRepositories;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServicesImp implements PatientService{

    private final PatientRepository patientRepository;

    private final ModelMapper modelMapper;


    @Autowired
    public PatientServicesImp(PatientRepository patientRepository, ModelMapper modelMapper) {
        this.patientRepository = patientRepository;
        this.modelMapper = modelMapper;

    }



    public List<PatientDto> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(patient -> modelMapper.map(patient, PatientDto.class))
                .collect(Collectors.toList());
    }

    public PatientDto getPatient(Long id){
        return patientRepository.findById(id)
                .map(patient -> modelMapper.map(patient,PatientDto.class))
                .orElseThrow(() -> new EntityNotFoundException("Patient with id " + id + " not found"));
    }

    @Transactional
    public PatientDto addDrug(Long id, PatientDrugDto patientDrugDto){
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient with id " + id + " not found"));
        patient.getDrugs().add(modelMapper.map(patientDrugDto, PatientDrug.class));
        Patient updatedPatient = patientRepository.save(patient);
        return modelMapper.map(updatedPatient, PatientDto.class);
    }
    @Transactional
    public PatientDto addAnalyse(Long id, AnalyseDto analyseDto){
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient with id " + id + " not found"));
        patient.getAnalyses().add(modelMapper.map(analyseDto, Analyse.class));
        Patient updatedPatient = patientRepository.save(patient);
        return modelMapper.map(updatedPatient, PatientDto.class);
    }
    @Transactional
    public PatientDto addInsuranceCompany(Long id, InsuranceCompanyDto insuranceCompanyDto){
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient with id " + id + " not found"));
        patient.getInsuranceCompanies().add(modelMapper.map(insuranceCompanyDto, InsuranceCompany.class));
        Patient updatedPatient = patientRepository.save(patient);
        return modelMapper.map(updatedPatient, PatientDto.class);
    }

    public PatientDto deleteDrug(Long id, PatientDrugDto patientDrugDto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient with id " + id + " not found"));
        PatientDrug patientDrug = modelMapper.map(patientDrugDto, PatientDrug.class);
        patient.getDrugs().remove(patientDrug);
        Patient updatedPatient = patientRepository.save(patient);
        return modelMapper.map(updatedPatient, PatientDto.class);
    }
    @Transactional
    public PatientDto deleteAnalyse(Long id, AnalyseDto analyseDto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient with id " + id + " not found"));
        Analyse analyse = modelMapper.map(analyseDto, Analyse.class);
        patient.getAnalyses().remove(analyse);
        Patient updatedPatient = patientRepository.save(patient);
        return modelMapper.map(updatedPatient, PatientDto.class);
    }
    @Transactional
    public PatientDto deleteInsuranceCompany(Long id, InsuranceCompanyDto insuranceCompanyDto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient with id " + id + " not found"));
        InsuranceCompany insuranceCompany = modelMapper.map(insuranceCompanyDto, InsuranceCompany.class);
        patient.getInsuranceCompanies().remove(insuranceCompany);
        Patient updatedPatient = patientRepository.save(patient);
        return modelMapper.map(updatedPatient, PatientDto.class);
    }

    @Transactional  // This annotation is important, it makes the method transactional
    public PatientDrugDto setDrugAlarm(PatientDrugDto patientDrugDto, List<DrugAlarmDto> paDrugAlarmDto, Long id){
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient with id " + id + " not found"));
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
                return modelMapper.map(patientDrug1, PatientDrugDto.class);  // Return updated PatientDrugDto
            }
        }
        // If we haven't found the matching drug, throw an exception or return a default value.
        throw new EntityNotFoundException("Drug with id " + patientDrugDto.getId() + " not found");
    }


    public List<MeasurementDto> getAllMeasurement(Long id){
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient with id " + id + " not found"));
        return patient.getMeasurements()
                .stream()
                .map(Measurement -> modelMapper.map(Measurement,MeasurementDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<MeasurementDto> addMeasurement(Long id, MeasurementDto measurementDto){
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient with id " + id + " not found"));
        patient.getMeasurements().add(modelMapper.map(measurementDto,Measurement.class));
        Patient updatedPatient = patientRepository.save(patient);
        return updatedPatient.getMeasurements()
                .stream()
                .map(Measurement -> modelMapper.map(Measurement, MeasurementDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<MeasurementDto> deleteMeasurement(Long id, MeasurementDto measurementDto){
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient with id " + id + " not found"));
        patient.getMeasurements().remove(modelMapper.map(measurementDto,Measurement.class));
        Patient updatedPatient = patientRepository.save(patient);
        return updatedPatient.getMeasurements()
                .stream()
                .map(Measurement -> modelMapper.map(Measurement, MeasurementDto.class))
                .collect(Collectors.toList());
    }
}


