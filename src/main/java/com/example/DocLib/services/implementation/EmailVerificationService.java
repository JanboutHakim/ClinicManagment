package com.example.DocLib.services.implementation;

import com.example.DocLib.models.User;
import com.example.DocLib.models.authentication.EmailVerificationToken;
import com.example.DocLib.repositories.EmailVerificationTokenRepository;
import com.example.DocLib.repositories.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {
    private final EmailVerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public void createVerification(User user) throws MessagingException {
        String otp = String.valueOf((int)(Math.random()*900000)+100000);
        EmailVerificationToken token = EmailVerificationToken.builder()
                .token(otp)
                .expiryDate(LocalDateTime.now().plusMinutes(10))
                .user(user)
                .build();
        tokenRepository.save(token);
        emailService.sendOtp(user.getEmail(), user.getUsername(), otp);
    }

    public boolean verifyToken(String username, String otp) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) return false;
        User user = userOpt.get();
        Optional<EmailVerificationToken> tokenOpt = tokenRepository.findByUser(user);
        if (tokenOpt.isEmpty()) return false;
        EmailVerificationToken token = tokenOpt.get();
        if (!token.getToken().equals(otp) || token.getExpiryDate().isBefore(LocalDateTime.now())) {
            return false;
        }
        user.setEmailVerified(true);
        userRepository.save(user);
        tokenRepository.delete(token);
        return true;
    }
}

