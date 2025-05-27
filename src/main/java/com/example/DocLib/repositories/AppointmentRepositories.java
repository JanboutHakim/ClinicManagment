package com.example.DocLib.repositories;

import com.example.DocLib.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepositories extends JpaRepository<Appointment,Long> {
}
