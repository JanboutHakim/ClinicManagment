package com.example.DocLib.repositories;

import com.example.DocLib.models.doctor.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepositories extends JpaRepository<Doctor,Long> {

}
