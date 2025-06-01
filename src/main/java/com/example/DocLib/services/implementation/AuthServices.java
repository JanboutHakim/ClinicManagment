package com.example.DocLib.services.implementation;

import com.example.DocLib.dto.UserDto;
import com.example.DocLib.models.User;
import com.example.DocLib.models.authentication.TokenResponse;
import com.example.DocLib.models.doctor.Doctor;
import com.example.DocLib.models.patient.Patient;
import com.example.DocLib.repositories.UserRepository;
import com.example.DocLib.security.JwtDecoder;
import com.example.DocLib.security.JwtIssuer;
import com.example.DocLib.configruation.UserPrincipleConfig;
import com.example.DocLib.security.JwtProperties;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;


@Service
@RequiredArgsConstructor
@Validated
public class AuthServices {
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtDecoder jwtDecoder;
    private final JwtProperties jwtProperties;

    public TokenResponse refreshToken(String refreshToken) {
        var decodedJWT = jwtDecoder.decode(refreshToken);

        if (!"refresh".equals(decodedJWT.getClaim("type").asString())) {
            throw new JwtException("Invalid token type");
        }

        var userId = Long.parseLong(decodedJWT.getSubject());
        var username = decodedJWT.getClaim("u").asString();

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var roles = List.of(user.getRole().toString());
        var newAccessToken = jwtIssuer.issueAccessToken(userId, username, roles);
        var newRefreshToken = jwtIssuer.issueRefreshToken(userId, username);

        return TokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .expiresIn(jwtProperties.getAccessTokenExpirationMs())
                .build();
    }

    public TokenResponse attemptLogin(String username, String password) {
        try {
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            var principal = (UserPrincipleConfig) authentication.getPrincipal();
            var roles = principal.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            var token = jwtIssuer.issueAccessToken(principal.getUserId(), principal.getUsername(), roles);
            var refreshToken = jwtIssuer.issueRefreshToken(principal.getUserId(), principal.getUsername());

            return TokenResponse.builder()
                    .accessToken(token)
                    .refreshToken(refreshToken)
                    .message("Login successful")
                    .success(true)
                    .user(modelMapper.map(userRepository.findByUsername(username), UserDto.class))
                    .build();

        } catch (BadCredentialsException e) {
            return TokenResponse.builder()
                    .message("Invalid username or password")
                    .success(false)
                    .build();

        } catch (UsernameNotFoundException e) {
            return TokenResponse.builder()
                    .message("User not found")
                    .success(false)
                    .build();

        } catch (Exception e) {
            return TokenResponse.builder()
                    .message("Authentication failed")
                    .success(false)
                    .build();
        }
    }

    @Transactional
    public UserDto registerUser(UserDto userDto) {
        validateUserDto(userDto);

        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        linkRoleSpecificEntity(userDto, user);

        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserDto.class);
    }


    private void validateUserDto(UserDto userDto) {
        if (userDto.getDoctor() != null && userDto.getPatient() != null) {
            throw new IllegalArgumentException("User cannot be both doctor and patient.");
        }
    }

    private void linkRoleSpecificEntity(UserDto userDto, User user) {
        switch (userDto.getRole()) {
            case DOCTOR -> {
                Doctor doctor = modelMapper.map(userDto.getDoctor(), Doctor.class);
                doctor.setUser(user);
                user.setDoctor(doctor);
            }
            case PATIENT -> {
                Patient patient = modelMapper.map(userDto.getPatient(), Patient.class);
                patient.setUser(user);
                user.setPatient(patient);
            }
            case ADMIN -> {

            }
            default -> throw new IllegalArgumentException("Unsupported role: " + userDto.getRole());
        }
    }


}
