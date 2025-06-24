package com.example.DocLib.services.implementation;

import com.example.DocLib.dto.UserDto;
import com.example.DocLib.exceptions.custom.AccessDeniedException;
import com.example.DocLib.exceptions.custom.ResourceNotFoundException;
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
import jakarta.mail.MessagingException;
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

import java.time.LocalDate;
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
    private final EmailVerificationService emailVerificationService;

    public TokenResponse refreshToken(String refreshToken) {
        var decodedJWT = jwtDecoder.decode(refreshToken);

        if (!"refresh".equals(decodedJWT.getClaim("type").asString())) {
            throw new JwtException("Invalid token type");
        }

        var userId = Long.parseLong(decodedJWT.getSubject());
        var username = decodedJWT.getClaim("u").asString();

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var roles = List.of("ROLE_" + user.getRole().toString());
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

            System.out.println("Login successful for user: " + username + " with roles: " + roles);

            var user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            if (Boolean.FALSE.equals(user.getEmailVerified())) {
                return TokenResponse.builder()
                        .message("Email not verified")
                        .success(false)
                        .build();
            }

            return generateTokens(user, roles);

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
            System.err.println("Authentication error: " + e.getMessage());
            return TokenResponse.builder()
                    .message("Authentication failed")
                    .success(false)
                    .build();
        }
    }

    @Transactional
    public UserDto registerUser(UserDto userDto) throws MessagingException {
        validateUserDto(userDto);

        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDate.now());

        linkRoleSpecificEntity(userDto, user);

        User savedUser = userRepository.save(user);

        emailVerificationService.createVerification(savedUser);

        return modelMapper.map(savedUser, UserDto.class);
    }

    public TokenResponse generateTokensForVerify(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User Not found"));
        var token = jwtIssuer.issueAccessToken(user.getId(), user.getUsername(), List.of(user.getRole().toString()));
        var refreshToken = jwtIssuer.issueRefreshToken(user.getId(), user.getUsername());

        return TokenResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .message("Login successful")
                .success(true)
                .expiresIn(jwtProperties.getAccessTokenExpirationMs())
                .user(modelMapper.map(user, UserDto.class))
                .build();
    }

    private TokenResponse generateTokens(User user, List<String> roles) {
        var token = jwtIssuer.issueAccessToken(user.getId(), user.getUsername(), roles);
        var refreshToken = jwtIssuer.issueRefreshToken(user.getId(), user.getUsername());

        return TokenResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .message("Login successful")
                .success(true)
                .expiresIn(jwtProperties.getAccessTokenExpirationMs())
                .user(modelMapper.map(user, UserDto.class))
                .build();
    }

    private void validateUserDto(UserDto userDto) {
        if (userDto.getDoctor() != null && userDto.getPatient() != null) {
            throw new IllegalArgumentException("User cannot be both doctor and patient.");
        }
    }

    private void linkRoleSpecificEntity(UserDto userDto, User user) {
        switch (userDto.getRole()) {
            case DOCTOR -> {
                Doctor doctor = userDto.getDoctor() != null ? 
                    modelMapper.map(userDto.getDoctor(), Doctor.class) : new Doctor();
                // Set doctor reference in all child objects
                if (doctor.getDoctorSchedules() != null) {
                    doctor.getDoctorSchedules().forEach(schedule -> schedule.setDoctor(doctor));
                }

                if (doctor.getHolidaySchedules() != null) {
                    doctor.getHolidaySchedules().forEach(holiday -> holiday.setDoctor(doctor));
                }

                if (doctor.getExperiences() != null) {
                    doctor.getExperiences().forEach(exp -> exp.setDoctor(doctor));
                }

                if (doctor.getScientificBackgrounds() != null) {
                    doctor.getScientificBackgrounds().forEach(bg -> bg.setDoctor(doctor));
                }

                if (doctor.getServices() != null) {
                    doctor.getServices().forEach(service -> service.setDoctor(doctor));
                }

                if (doctor.getAppointments() != null) {
                    doctor.getAppointments().forEach(appointment -> appointment.setDoctor(doctor));
                }
                if(doctor.getSpecialization() !=null){
                    doctor.getSpecialization().forEach(specializations -> specializations.setDoctor(doctor));
                }

                if (doctor.getInsuranceCompanies() != null) {
                    // Usually many-to-many — handled via mappedBy or cascade
                    // Ensure doctor is saved first if needed
                }
                doctor.setUser(user);
                user.setDoctor(doctor);
            }
            case PATIENT -> {
                Patient patient = userDto.getPatient() != null ? 
                    modelMapper.map(userDto.getPatient(), Patient.class) : new Patient();
                patient.setUser(user);
                user.setPatient(patient);
            }
            case ADMIN -> {
                // Admin doesn't need additional entity
            }
            default -> throw new IllegalArgumentException("Unsupported role: " + userDto.getRole());
        }
    }

    public Long extractUserId(String token) {
        var decodedJWT = jwtDecoder.decode(token);

        if (!"access".equals(decodedJWT.getClaim("type").asString())) {
            throw new JwtException("Invalid token type");
        }

        return Long.parseLong(decodedJWT.getSubject());
    }

    public static Long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserPrincipleConfig user) {
            return user.getUserId();
        }
        throw new AccessDeniedException("Invalid authentication");
    }

    public static List<? extends GrantedAuthority> getCurrentUserRole() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserPrincipleConfig user) {
            return user.getAuthorities().stream()
                    .toList();
        }
        throw new AccessDeniedException("Invalid authentication");
    }

    public boolean verifyEmail(String username, String otp) {
        return emailVerificationService.verifyToken(username, otp);
    }
}