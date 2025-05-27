package com.example.DocLib.services;

import com.example.DocLib.repositories.UserRepositories;
import com.example.DocLib.security.UserPrincipleConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepositories userRepositories;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       var user = userRepositories.findByUsername(username).orElseThrow();
        System.out.println(user.toString());
        return UserPrincipleConfig.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString())))
                .password(user.getPassword())
                .build();
    }
}
