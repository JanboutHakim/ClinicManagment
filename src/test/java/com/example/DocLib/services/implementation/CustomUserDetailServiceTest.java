package com.example.DocLib.services.implementation;

import com.example.DocLib.enums.Roles;
import com.example.DocLib.models.User;
import com.example.DocLib.configruation.UserPrincipleConfig;
import com.example.DocLib.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailServiceTest {
    @Test
    void loadUserByUsernameReturnsPrincipal() {
        UserRepository repo = mock(UserRepository.class);
        CustomUserDetailService service = new CustomUserDetailService(repo);

        User user = User.builder()
                .id(1L)
                .username("bob")
                .password("pw")
                .role(Roles.ADMIN)
                .build();
        when(repo.findByUsername("bob")).thenReturn(Optional.of(user));

        UserDetails details = service.loadUserByUsername("bob");

        assertTrue(details instanceof UserPrincipleConfig);
        UserPrincipleConfig principal = (UserPrincipleConfig) details;
        assertEquals(1L, principal.getUserId());
        assertEquals("bob", principal.getUsername());
        assertEquals("pw", principal.getPassword());
        assertEquals(1, principal.getAuthorities().size());
        assertEquals("ROLE_ADMIN", principal.getAuthorities().iterator().next().getAuthority());
    }
}
