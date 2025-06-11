package com.example.DocLib.configruation;

import com.example.DocLib.exceptions.CustomAuthenticationEntryPoint;
import com.example.DocLib.security.CustomAccessDeniedHandler;
import com.example.DocLib.security.JwtAuthenticationFilter;
import com.example.DocLib.services.implementation.CustomUserDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailService customUserDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .securityMatcher("/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/auth/register", "/auth/login","/app/**","/ws/**").permitAll()
           //             .requestMatchers("/**").hasRole("ADMIN")
                        .requestMatchers("/doctor/**","/appointments/**").hasRole("DOCTOR")
                        .requestMatchers("/patient/**","/appointments/**").hasRole("PATIENT")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint( new ObjectMapper()))

                )
           //     .httpBasic(Customizer.withDefaults())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }



    private final PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, CustomUserDetailService customUserDetailService, PasswordEncoder passwordEncoder) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customUserDetailService = customUserDetailService;
        this.passwordEncoder = passwordEncoder;
    }


   @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
       var builder = http.getSharedObject(AuthenticationManagerBuilder.class);
       builder
               .userDetailsService(this.customUserDetailService)
               .passwordEncoder(this.passwordEncoder);
      return builder.build();
   }

}
