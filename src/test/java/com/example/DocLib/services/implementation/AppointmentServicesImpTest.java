package com.example.DocLib.services.implementation;

import com.example.DocLib.dto.doctor.DoctorDto;
import com.example.DocLib.enums.AppointmentStatus;
import com.example.DocLib.repositories.AppointmentRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentServicesImpTest {

    @Test
    void isPatientAvailableChecksRepository() {
        AppointmentRepository repo = mock(AppointmentRepository.class);
        DoctorServicesImp doctor = mock(DoctorServicesImp.class);
        AppointmentServicesImp service = new AppointmentServicesImp(new ModelMapper(), repo,
                mock(SimpMessagingTemplate.class), doctor,
                mock(PatientServicesImp.class), mock(UserServicesImp.class));

        LocalDateTime time = LocalDateTime.now();
        DoctorDto dto = new DoctorDto();
        dto.setCheckupDurationInMinutes(30);
        when(doctor.getDoctorById(1L)).thenReturn(dto);
        when(repo.findOverlappingAppointmentsForPatient(eq(2L), eq(time), eq(time.plusMinutes(30)), anyList()))
                .thenReturn(Collections.emptyList());

        assertTrue(service.isPatientAvailable(2L, 1L, time));
        verify(repo).findOverlappingAppointmentsForPatient(eq(2L), eq(time), eq(time.plusMinutes(30)), anyList());
    }

    @Test
    void isDoctorAvailableChecksOverlaps() {
        AppointmentRepository repo = mock(AppointmentRepository.class);
        DoctorServicesImp doctor = mock(DoctorServicesImp.class);
        AppointmentServicesImp service = new AppointmentServicesImp(new ModelMapper(), repo,
                mock(SimpMessagingTemplate.class), doctor,
                mock(PatientServicesImp.class), mock(UserServicesImp.class));

        LocalDateTime start = LocalDateTime.now();
        DoctorDto dto = new DoctorDto();
        dto.setCheckupDurationInMinutes(30);
        when(doctor.getDoctorById(1L)).thenReturn(dto);
        when(repo.findOverlappingAppointments(eq(1L), eq(start), eq(start.plusMinutes(30)),
                anyList())).thenReturn(Collections.emptyList());

        assertTrue(service.isDoctorAvailable(1L, start));
    }

    @Test
    void getCachedCheckupDurationReturnsFromDoctorService() {
        AppointmentRepository repo = mock(AppointmentRepository.class);
        DoctorServicesImp doctor = mock(DoctorServicesImp.class);
        DoctorDto dto = new DoctorDto();
        dto.setCheckupDurationInMinutes(20);
        when(doctor.getDoctorById(1L)).thenReturn(dto);

        AppointmentServicesImp service = new AppointmentServicesImp(new ModelMapper(), repo,
                mock(SimpMessagingTemplate.class), doctor,
                mock(PatientServicesImp.class), mock(UserServicesImp.class));

        assertEquals(20, service.getCachedCheckupDuration(1L));
    }
}
