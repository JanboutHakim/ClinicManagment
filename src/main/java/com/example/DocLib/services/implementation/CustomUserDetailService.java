package com.example.DocLib.services.implementation;

import com.example.DocLib.repositories.UserRepository;
import com.example.DocLib.configruation.UserPrincipleConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
@Component
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       var user = userRepository.findByUsername(username).orElseThrow();
        System.out.println(user.toString());
        return UserPrincipleConfig.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString())))
                .password(user.getPassword())
                .build();
    }
}
