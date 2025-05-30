package com.example.DocLib;

import com.example.DocLib.dto.doctor.ExperienceDto;
import com.example.DocLib.models.doctor.Experience;
import com.example.DocLib.security.JwtProperties;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class DocLibApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocLibApplication.class, args);
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
