package com.example.DocLib.repositories;

import com.example.DocLib.models.User;
import com.example.DocLib.models.authentication.EmailVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {
    Optional<EmailVerificationToken> findByToken(String token);
    Optional<EmailVerificationToken> findByUser(User user);
}
