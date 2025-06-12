package com.example.DocLib.controllers;

import com.example.DocLib.configruation.UserPrincipleConfig;
import com.example.DocLib.dto.appointment.AppointmentDto;
import com.example.DocLib.security.UserPrincipleAuthenticationToken;
import com.example.DocLib.services.implementation.AppointmentServicesImp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

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
        setAuth(2L, "ROLE_DOCTOR");

        AppointmentDto dto = new AppointmentDto();
        when(service.cancelAppointmentByClinic(3L, "r")).thenReturn(dto);

        ResponseEntity<AppointmentDto> result = controller.cancelAppointmentByDoctor(3L, "r", 2L);

        assertSame(dto, result.getBody());
        verify(service).cancelAppointmentByClinic(3L, "r");
    }
}
