package com.example.DocLib.services.implementation;

import com.example.DocLib.dto.UserDto;
import com.example.DocLib.models.User;
import com.example.DocLib.models.authentication.ApiResponse;
import com.example.DocLib.models.doctor.Doctor;
import com.example.DocLib.models.patient.Patient;
import com.example.DocLib.repositories.UserRepositories;
import com.example.DocLib.security.JwtIssuer;
import com.example.DocLib.configruation.UserPrincipleConfig;
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


@Service
@RequiredArgsConstructor
@Validated
public class AuthServices {
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final UserRepositories userRepositories;
    private final PasswordEncoder passwordEncoder;


    public ApiResponse<UserDto> attemptLogin(String username, String password) {
        try {
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            var principal = (UserPrincipleConfig) authentication.getPrincipal();
            var roles = principal.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            var token = jwtIssuer.issue(principal.getUserId(), principal.getUsername(), roles);

            return ApiResponse.<UserDto>builder()
                    .accessToken(token)
                    .message("Login successful")
                    .success(true)
                    .data(modelMapper.map(userRepositories.findByUsername(username), UserDto.class))
                    .build();

        } catch (BadCredentialsException e) {
            return ApiResponse.<UserDto>builder()
                    .message("Invalid username or password")
                    .success(false)
                    .build();

        } catch (UsernameNotFoundException e) {
            return ApiResponse.<UserDto>builder()
                    .message("User not found")
                    .success(false)
                    .build();

        } catch (Exception e) {
            return ApiResponse.<UserDto>builder()
                    .message("Authentication failed")
                    .success(false)
                    .build();
        }
    }
    @Transactional
    public UserDto registerUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);

        // Only map and link Doctor if it's present
        if (userDto.getDoctor() != null) {
            Doctor doctor = modelMapper.map(userDto.getDoctor(), Doctor.class);
            doctor.setUser(user);  // Required due to @MapsId
            user.setDoctor(doctor);  // Required for cascading save
        }
        else if(userDto.getPatient() != null){
            Patient patient = modelMapper.map(userDto.getPatient(), Patient.class);
            patient.setUser(user);  // Required due to @MapsId
            user.setPatient(patient);  // Required for cascading save
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepositories.save(user);  // Will also save Doctor due to cascade

        return modelMapper.map(savedUser,UserDto.class);
    }

}
