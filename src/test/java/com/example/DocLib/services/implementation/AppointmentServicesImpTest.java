package com.example.DocLib.services.implementation;

import com.example.DocLib.dto.doctor.DoctorDto;
import com.example.DocLib.dto.appointment.AppointmentDto;
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
        AppointmentServicesImp service = new AppointmentServicesImp(new ModelMapper(), repo,
                mock(SimpMessagingTemplate.class), mock(DoctorServicesImp.class),
                mock(PatientServicesImp.class), mock(UserServicesImp.class));

        LocalDateTime time = LocalDateTime.now();
        when(repo.existsByPatientIdAndStartTime(2L, time)).thenReturn(false);

        assertTrue(service.isPatientAvailable(2L, time));
        verify(repo).existsByPatientIdAndStartTime(2L, time);
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

    @Test
    void addAppointmentComputesEndTimeFromDuration() {
        AppointmentRepository repo = mock(AppointmentRepository.class);
        DoctorServicesImp doctor = mock(DoctorServicesImp.class);
        SimpMessagingTemplate template = mock(SimpMessagingTemplate.class);
        DoctorDto doctorDto = new DoctorDto();
        doctorDto.setCheckupDurationInMinutes(30);
        when(doctor.getDoctorById(1L)).thenReturn(doctorDto);

        AppointmentServicesImp service = new AppointmentServicesImp(new ModelMapper(), repo,
                template, doctor, mock(PatientServicesImp.class), mock(UserServicesImp.class));

        AppointmentDto dto = new AppointmentDto();
        dto.setDoctorId(1L);
        dto.setPatientId(2L);
        LocalDateTime start = LocalDateTime.of(2024, 1, 1, 10, 0);
        dto.setStartTime(start);

        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        AppointmentDto result = service.addAppointment(dto);

        assertEquals(start.plusMinutes(30), result.getEndTime());
        verify(repo).save(any());
    }
}
