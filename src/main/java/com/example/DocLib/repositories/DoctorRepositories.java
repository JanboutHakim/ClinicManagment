package com.example.DocLib.repositories;

import com.example.DocLib.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepositories extends JpaRepository<Doctor,Long> {
}
