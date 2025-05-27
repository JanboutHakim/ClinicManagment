package com.example.DocLib.repositories;

import com.example.DocLib.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositories extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

}
