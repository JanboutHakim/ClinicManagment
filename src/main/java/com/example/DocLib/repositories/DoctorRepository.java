package com.example.DocLib.repositories;

import com.example.DocLib.models.Drug;
import com.example.DocLib.models.doctor.Doctor;
import com.example.DocLib.models.doctor.DoctorHolidaySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Long> {

        @Query("SELECT h FROM Doctor d JOIN d.holidaySchedules h WHERE " +
                "d.id = :doctorId AND " +
                "(h.startTime <= :startTime AND h.endTime >= :startTime)")
        List<DoctorHolidaySchedule> findHolidayScheduleByDate(@Param("doctorId") Long doctorId,
                                                        @Param("startTime") LocalDateTime startTime);
        @Query("SELECT d FROM Doctor d WHERE " +
                "LOWER(d.clinicName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
                "LOWER(d.address) LIKE LOWER(CONCAT('%', :query, '%')) ")
        List<Doctor> searchDoctor(@Param("query") String query);
}
