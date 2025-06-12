package com.example.DocLib.controllers;

import com.example.DocLib.configruation.UserPrincipleConfig;
import com.example.DocLib.dto.AddressDto;
import com.example.DocLib.dto.doctor.DoctorDto;
import com.example.DocLib.security.UserPrincipleAuthenticationToken;
import com.example.DocLib.services.implementation.AppointmentServicesImp;
import com.example.DocLib.services.implementation.DoctorServicesImp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DoctorControllerTest {

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    private void setAuth(Long id) {
        UserPrincipleConfig cfg = UserPrincipleConfig.builder()
                .userId(id)
                .username("doc")
                .authorities(Collections.singleton(() -> "ROLE_DOCTOR"))
                .build();
        SecurityContextHolder.getContext().setAuthentication(new UserPrincipleAuthenticationToken(cfg));
    }

    @Test
    void updateAddressDelegatesToService() {
        DoctorServicesImp service = mock(DoctorServicesImp.class);
        AppointmentServicesImp app = mock(AppointmentServicesImp.class);
        DoctorController controller = new DoctorController(service, app);
        setAuth(1L);

        AddressDto address = new AddressDto();
        address.setAddress("new");
        DoctorDto dto = new DoctorDto();
        when(service.updateAddress(1L, "new")).thenReturn(dto);

        ResponseEntity<DoctorDto> result = controller.updateAddress(1L, address);

        assertSame(dto, result.getBody());
        verify(service).updateAddress(1L, "new");
    }

    @Test
    void getDoctorByIdReturnsFromService() {
        DoctorServicesImp service = mock(DoctorServicesImp.class);
        AppointmentServicesImp app = mock(AppointmentServicesImp.class);
        DoctorController controller = new DoctorController(service, app);

        DoctorDto dto = new DoctorDto();
        when(service.getDoctorById(3L)).thenReturn(dto);

        ResponseEntity<DoctorDto> result = controller.getDoctorById(3L);

        assertSame(dto, result.getBody());
        verify(service).getDoctorById(3L);
    }

    @Test
    void getAllDoctorsReturnsList() {
        DoctorServicesImp service = mock(DoctorServicesImp.class);
        AppointmentServicesImp app = mock(AppointmentServicesImp.class);
        DoctorController controller = new DoctorController(service, app);

        when(service.getAllDoctors()).thenReturn(Collections.emptyList());

        ResponseEntity<List<DoctorDto>> result = controller.getAllDoctors();

        assertNotNull(result.getBody());
        verify(service).getAllDoctors();
    }

    @Test
    void getDoctorAvailableSlotsUsesAppointmentService() {
        DoctorServicesImp service = mock(DoctorServicesImp.class);
        AppointmentServicesImp app = mock(AppointmentServicesImp.class);
        DoctorController controller = new DoctorController(service, app);

        when(app.getDoctorAvailableSlots(7L)).thenReturn(Collections.emptyList());

        ResponseEntity<List<LocalDateTime>> result = controller.getDoctorAvailableSlots(7L);

        assertNotNull(result.getBody());
        verify(app).getDoctorAvailableSlots(7L);
    }
}
