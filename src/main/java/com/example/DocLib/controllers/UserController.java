package com.example.DocLib.controllers;

import com.example.DocLib.services.implementation.UserServicesImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserServicesImp userServicesImp;

    public UserController(UserServicesImp userServicesImp) {
        this.userServicesImp = userServicesImp;
    }

    @PostMapping("/{id}/addImage")
    public ResponseEntity<String> saveImage(@RequestParam MultipartFile image,
                                            @PathVariable Long id) throws IOException {
        String imagePath = userServicesImp.saveImage(image);
        userServicesImp.setUserImagePath(id,imagePath);
        return ResponseEntity.ok(imagePath);
    }


}
