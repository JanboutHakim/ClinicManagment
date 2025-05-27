package com.example.DocLib.services;

import com.example.DocLib.dto.UserDto;
import com.example.DocLib.models.User;
import com.example.DocLib.models.authentication.ApiResponse;
import com.example.DocLib.models.authentication.LoginResponse;
import com.example.DocLib.repositories.UserRepositories;
import com.example.DocLib.security.JwtIssuer;
import com.example.DocLib.security.UserPrincipleConfig;
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
        User newUser = modelMapper.map(userDto, User.class);
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUser = userRepositories.save(newUser);
        return modelMapper.map(savedUser,UserDto.class);
    }

}
