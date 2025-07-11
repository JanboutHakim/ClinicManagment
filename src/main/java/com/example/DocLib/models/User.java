package com.example.DocLib.models;
import com.example.DocLib.enums.Gender;
import com.example.DocLib.enums.Roles;
import com.example.DocLib.models.doctor.Doctor;
import com.example.DocLib.models.patient.Patient;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name="User")
public  class User {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username",nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "email")
    private String email;

    @Enumerated
    @Column(name = "role")
    private Roles role;


    @Builder.Default
    @Column(name = "email_verified")
    private Boolean emailVerified = false;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Doctor doctor;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Patient patient;

}
