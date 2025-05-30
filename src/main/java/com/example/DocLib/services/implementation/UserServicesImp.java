package com.example.DocLib.services.implementation;

import com.example.DocLib.dto.UserDto;
import com.example.DocLib.models.User;
import com.example.DocLib.repositories.UserRepositories;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServicesImp {
    private final UserRepositories userRepositories;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServicesImp(UserRepositories userRepositories, PasswordEncoder passwordEncoder, ModelMapper modelMapper){
        this.userRepositories = userRepositories;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }


    public Optional<UserDto> findByUsername(String username) {
        Optional<User> userOptional = userRepositories.findByUsername(username);
        return userOptional.map(user -> modelMapper.map(user, UserDto.class));
    }

    public List<UserDto> getAllUsers() {
        List<User> userList = userRepositories.findAll();
        return userList.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public Optional<UserDto> findById(Long id) {
        Optional<User> userOptional = userRepositories.findById(id);
        return userOptional.map(user -> modelMapper.map(user, UserDto.class));
    }
    @Transactional
    public void deleteUser(Long id) {
        userRepositories.deleteById(id);
    }
    @Transactional
    public UserDto updateUser(Long id, UserDto userDto) {
        Optional<User> userOptional = userRepositories.findById(id);
        if (userOptional.isPresent()) {
            User user = modelMapper.map(userDto,User.class);
            User updatedUser = userRepositories.save(user);
            return modelMapper.map(updatedUser, UserDto.class);
        } else {
            throw new NoSuchElementException("User not found with id: " + id);
        }
    }

}
