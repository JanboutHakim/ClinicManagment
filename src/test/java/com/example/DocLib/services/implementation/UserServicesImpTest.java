package com.example.DocLib.services.implementation;

import com.example.DocLib.dto.UserDto;
import com.example.DocLib.models.User;
import com.example.DocLib.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServicesImpTest {
    @Test
    void findByUsernameMapsUser() {
        UserRepository repo = mock(UserRepository.class);
        PasswordEncoder enc = mock(PasswordEncoder.class);
        ModelMapper mapper = new ModelMapper();
        UserServicesImp service = new UserServicesImp(repo, enc, mapper);

        User user = User.builder().id(1L).username("name").build();
        when(repo.findByUsername("name")).thenReturn(Optional.of(user));

        Optional<UserDto> result = service.findByUsername("name");

        assertTrue(result.isPresent());
        assertEquals("name", result.get().getUsername());
    }

    @Test
    void updateUserSavesMappedUser() {
        UserRepository repo = mock(UserRepository.class);
        PasswordEncoder enc = mock(PasswordEncoder.class);
        ModelMapper mapper = new ModelMapper();
        UserServicesImp service = new UserServicesImp(repo, enc, mapper);

        UserDto dto = UserDto.builder().id(3L).username("new").password("p").build();
        when(repo.findById(3L)).thenReturn(Optional.of(new User()));
        when(repo.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserDto result = service.updateUser(3L, dto);

        assertEquals("new", result.getUsername());
    }

    @Test
    void getUserReturnsMappedUser() {
        UserRepository repo = mock(UserRepository.class);
        PasswordEncoder enc = mock(PasswordEncoder.class);
        ModelMapper mapper = new ModelMapper();
        UserServicesImp service = new UserServicesImp(repo, enc, mapper);

        User user = User.builder().id(5L).username("bob").build();
        when(repo.findById(5L)).thenReturn(Optional.of(user));

        UserDto result = service.getUser(5L);

        assertEquals("bob", result.getUsername());
    }
}
