package com.example.DocLib.services.implementation;

import com.example.DocLib.dto.UserDto;
import com.example.DocLib.exceptions.custom.ResourceNotFoundException;
import com.example.DocLib.models.User;
import com.example.DocLib.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServicesImp {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServicesImp(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }


    public Optional<UserDto> findByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.map(user -> modelMapper.map(user, UserDto.class));
    }

    public List<UserDto> getAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public Optional<UserDto> findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(user -> modelMapper.map(user, UserDto.class));
    }
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public UserDto updateUser(Long id, UserDto userDto) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = modelMapper.map(userDto,User.class);
            User updatedUser = userRepository.save(user);
            return modelMapper.map(updatedUser, UserDto.class);
        } else {
            throw new NoSuchElementException("User not found with id: " + id);
        }
    }
    @Transactional
    public void setUserImagePath(Long userId,String imagePath){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User not exist"));
        user.setImageUrl(imagePath);
    }

    public String saveImage(MultipartFile file) throws IOException {
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String uploadDir = "uploads";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return "/images/" + filename;
    }

    public UserDto getUser(Long userId){
        return convertToDto(userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User Not Found")));
    }

    public UserDto convertToDto(User user){
        return modelMapper.map(user,UserDto.class);
    }


}
