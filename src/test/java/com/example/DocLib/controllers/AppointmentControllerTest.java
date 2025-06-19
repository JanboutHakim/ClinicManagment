package com.example.DocLib.controllers;

import com.example.DocLib.configruation.UserPrincipleConfig;
import com.example.DocLib.dto.StringDto;
import com.example.DocLib.dto.appointment.AppointmentDto;
import com.example.DocLib.security.UserPrincipleAuthenticationToken;
import com.example.DocLib.services.implementation.AppointmentServicesImp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentControllerTest {

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    private void setAuth(Long id, String role) {
        UserPrincipleConfig cfg = UserPrincipleConfig.builder()
                .userId(id)
                .username("u")
                .authorities(Collections.singleton(() -> role))
                .build();
        SecurityContextHolder.getContext()
                .setAuthentication(new UserPrincipleAuthenticationToken(cfg));
    }

    @Test
    void getAppointmentByIdReturnsFromService() {
        AppointmentServicesImp service = mock(AppointmentServicesImp.class);
        AppointmentController controller = new AppointmentController(service);
        setAuth(1L, "ROLE_PATIENT");

        AppointmentDto dto = new AppointmentDto();
        when(service.getAppointmentById(5L)).thenReturn(dto);

        ResponseEntity<AppointmentDto> result = controller.getAppointmentById(1L,5L);

        assertSame(dto, result.getBody());
        verify(service).getAppointmentById(5L);
    }

    @Test
    void cancelAppointmentByDoctorUsesService() {
        AppointmentServicesImp service = mock(AppointmentServicesImp.class);
        AppointmentController controller = new AppointmentController(service);
        setAuth(2L, "DOCTOR");

        AppointmentDto dto = new AppointmentDto();
        when(service.cancelAppointmentByClinic(3L, "r")).thenReturn(dto);

        ResponseEntity<AppointmentDto> result = controller.cancelAppointmentByDoctor(3L, new StringDto("r"), 2L);

        assertSame(dto, result.getBody());
        verify(service).cancelAppointmentByClinic(3L, "r");
    }

    @Test
    void addAppointmentDelegatesToService() {
        AppointmentServicesImp service = mock(AppointmentServicesImp.class);
        AppointmentController controller = new AppointmentController(service);
        setAuth(5L, "ROLE_PATIENT");

        AppointmentDto dto = new AppointmentDto();
        when(service.addAppointment(dto)).thenReturn(dto);

        ResponseEntity<AppointmentDto> result = controller.addAppointment(5L, dto);

        assertSame(dto, result.getBody());
        verify(service).addAppointment(dto);
    }

    @Test
    void deleteAppointmentInvokesService() {
        AppointmentServicesImp service = mock(AppointmentServicesImp.class);
        AppointmentController controller = new AppointmentController(service);
        setAuth(4L, "ROLE_PATIENT");

        ResponseEntity<Void> result = controller.deleteAppointment(7L, 4L);

        assertEquals(204, result.getStatusCode().value());
        verify(service).deleteAppointment(7L);
    }

    @Test
    void getAppointmentsByDoctorReturnsList() {
        AppointmentServicesImp service = mock(AppointmentServicesImp.class);
        AppointmentController controller = new AppointmentController(service);
        setAuth(6L, "ROLE_PATIENT");

        when(service.getAppointmentsByDoctor(3L)).thenReturn(Collections.emptyList());

        ResponseEntity<List<AppointmentDto>> result = controller.getAppointmentsByDoctor(3L, 6L);

        assertNotNull(result.getBody());
        verify(service).getAppointmentsByDoctor(3L);
    }

    @Test
    void confirmAppointmentDelegatesToService() {
        AppointmentServicesImp service = mock(AppointmentServicesImp.class);
        AppointmentController controller = new AppointmentController(service);
        setAuth(8L, "ROLE_DOCTOR");

        AppointmentDto dto = new AppointmentDto();
        when(service.confirmAppointment(9L)).thenReturn(dto);

        ResponseEntity<AppointmentDto> result = controller.confirmAppointment(9L, 8L);

        assertSame(dto, result.getBody());
        verify(service).confirmAppointment(9L);
    }
}
