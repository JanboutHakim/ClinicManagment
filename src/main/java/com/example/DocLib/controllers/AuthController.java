package com.example.DocLib.controllers;

import com.example.DocLib.dto.RefreshTokenRequest;
import com.example.DocLib.dto.UserDto;
import com.example.DocLib.dto.OtpVerificationRequest;
import com.example.DocLib.models.authentication.ApiResponse;
import com.example.DocLib.models.authentication.LoginRequest;
import com.example.DocLib.models.authentication.TokenResponse;
import com.example.DocLib.services.implementation.AuthServices;
import com.example.DocLib.services.implementation.UserServicesImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserServicesImp userServicesImp;
    private final AuthServices authServices;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserServicesImp userServicesImp, AuthServices authServices, PasswordEncoder passwordEncoder) {
        this.userServicesImp = userServicesImp;
        this.authServices = authServices;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user.
     *
     * @param userDto The UserDto object containing the user information to be registered.
     * @return ResponseEntity containing the created UserDto if registration is successful.
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> register(@RequestBody @Valid UserDto userDto) {
        UserDto createdUser = authServices.registerUser(userDto);
        ApiResponse<UserDto> response = ApiResponse.<UserDto>builder()
                .data(createdUser)
                .message("User registered. Check email for verification code")
                .success(true)
                .accessToken(null)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<Void>> verifyOtp(@RequestBody @Valid OtpVerificationRequest request) {
        boolean verified = authServices.verifyEmail(request.getUsername(), request.getOtp());
        if (verified) {
            return ResponseEntity.ok(ApiResponse.<Void>builder()
                    .message("Email verified")
                    .success(true)
                    .build());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse.<Void>builder()
                        .message("Invalid or expired OTP")
                        .success(false)
                        .build()
        );
    }


    /**
     * Logs in the user using the provided credentials.
     *
     * @param request The login request containing the username and password.
     * @return ApiResponse containing the user data if login is successful, or an error message if login fails.
     */
    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> loginUser(@RequestBody @Validated LoginRequest request){
        return ResponseEntity.ok(authServices.attemptLogin(request.getUsername(),request.getPassword()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
        return ResponseEntity.ok(authServices.refreshToken(request.getRefreshToken()));
    }



}
