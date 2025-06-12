package com.example.DocLib.controllers;

import com.example.DocLib.configruation.UserPrincipleConfig;
import com.example.DocLib.dto.doctor.DoctorDto;
import com.example.DocLib.dto.patient.PatientDto;
import com.example.DocLib.dto.patient.PatientDrugDto;
import com.example.DocLib.dto.patient.PatientHistoryRecordDto;
import com.example.DocLib.security.UserPrincipleAuthenticationToken;
import com.example.DocLib.services.implementation.PatientServicesImp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientControllerTest {

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    private void setAuth(Long id) {
        UserPrincipleConfig cfg = UserPrincipleConfig.builder()
                .userId(id)
                .username("pat")
                .authorities(Collections.singleton(() -> "ROLE_PATIENT"))
                .build();
        SecurityContextHolder.getContext().setAuthentication(new UserPrincipleAuthenticationToken(cfg));
    }

    @Test
    void deleteMeasurementDelegatesToService() {
        PatientServicesImp service = mock(PatientServicesImp.class);
        PatientController controller = new PatientController(service);
        setAuth(1L);

        PatientDto dto = new PatientDto();
        when(service.deleteMeasurement(1L, 2L)).thenReturn(dto);

        ResponseEntity<PatientDto> result = controller.deleteMeasurement(1L, 2L);

        assertSame(dto, result.getBody());
        verify(service).deleteMeasurement(1L, 2L);
    }

    @Test
    void getPatientDoctorsReturnsList() {
        PatientServicesImp service = mock(PatientServicesImp.class);
        PatientController controller = new PatientController(service);
        setAuth(3L);

        List<DoctorDto> doctors = Arrays.asList(new DoctorDto(), new DoctorDto());
        when(service.getPatientDoctors(3L)).thenReturn(doctors);

        ResponseEntity<List<DoctorDto>> result = controller.getPatientDoctors(3L);

        assertEquals(2, result.getBody().size());
        verify(service).getPatientDoctors(3L);
    }

    @Test
    void updateDrugSetsIdAndCallsService() {
        PatientServicesImp service = mock(PatientServicesImp.class);
        PatientController controller = new PatientController(service);
        setAuth(4L);

        PatientDrugDto drugDto = new PatientDrugDto();
        when(service.updateDrug(4L, drugDto)).thenReturn(new PatientDto());

        ResponseEntity<PatientDto> result = controller.updateDrug(4L, 9L, drugDto);

        assertNotNull(result.getBody());
        assertEquals(9L, drugDto.getId());
        verify(service).updateDrug(4L, drugDto);
    }

    @Test
    void updateHistoryRecordDelegatesToService() {
        PatientServicesImp service = mock(PatientServicesImp.class);
        PatientController controller = new PatientController(service);
        setAuth(5L);

        PatientHistoryRecordDto dto = new PatientHistoryRecordDto();
        when(service.updateHistoryRecord(5L, dto)).thenReturn(new PatientDto());

        ResponseEntity<PatientDto> result = controller.updateHistoryRecord(5L, 11L, dto);

        assertNotNull(result.getBody());
        assertEquals(11L, dto.getId());
        verify(service).updateHistoryRecord(5L, dto);
    }
}
