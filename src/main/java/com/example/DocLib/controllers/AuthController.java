package com.example.DocLib.controllers;

import com.example.DocLib.dto.UserDto;
import com.example.DocLib.models.authentication.ApiResponse;
import com.example.DocLib.models.authentication.LoginRequest;
import com.example.DocLib.security.UserPrincipleConfig;
import com.example.DocLib.services.AuthServices;
import com.example.DocLib.services.UserServicesImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/hi")
    public String greating(){
        return "HelloWorld";
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }
        System.out.println(bindingResult.toString());

        UserDto createdUser = authServices.registerUser(userDto);
        return ResponseEntity.ok(createdUser);
    }


    @PostMapping("/login")
    public ApiResponse<UserDto> loginUser(@RequestBody @Validated LoginRequest request){
        return authServices.attemptLogin(request.getUsername(),request.getPassword());
    }

    @GetMapping("/secured")
    public String secured(@AuthenticationPrincipal UserPrincipleConfig userPrincipleConfig){
        return "if use see this you are logged in as :"
                + userPrincipleConfig.getUsername()+"and your id is "+ userPrincipleConfig.getUserId();
    }


}
