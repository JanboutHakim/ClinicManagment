package com.example.DocLib.repositories;

import com.example.DocLib.enums.AppointmentStatus;
import com.example.DocLib.models.appointment.Appointment;
import com.example.DocLib.models.doctor.Doctor;
import com.example.DocLib.models.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    int countByStatus(AppointmentStatus status);
    int countByDoctorId(Long doctorId);
    int countByDoctorIdAndStatusIn(Long doctorId, List<AppointmentStatus> statuses);

    List<Appointment> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId " +
            "AND a.status IN :statuses " +
            "AND ((:startTime BETWEEN a.startTime AND a.endTime) OR " +
            "(:endTime BETWEEN a.startTime AND a.endTime) OR " +
            "(a.startTime BETWEEN :startTime AND :endTime))")
    List<Appointment> findOverlappingAppointments(@Param("doctorId") Long doctorId,
                                                  @Param("startTime") LocalDateTime startTime,
                                                  @Param("endTime") LocalDateTime endTime,
                                                  @Param("statuses") List<AppointmentStatus> statuses);

    @Query("SELECT a FROM Appointment a WHERE a.patient.id = :patientId " +
            "AND a.status IN :statuses " +
            "AND ((:startTime BETWEEN a.startTime AND a.endTime) OR " +
            "(:endTime BETWEEN a.startTime AND a.endTime) OR " +
            "(a.startTime BETWEEN :startTime AND :endTime))")
    List<Appointment> findOverlappingAppointmentsForPatient(@Param("patientId") Long patientId,
                                                            @Param("startTime") LocalDateTime startTime,
                                                            @Param("endTime") LocalDateTime endTime,
                                                            @Param("statuses") List<AppointmentStatus> statuses);
    @Query("SELECT a FROM Appointment a WHERE " +
            "((:startTime BETWEEN a.startTime AND a.endTime) OR " +
            "(:endTime BETWEEN a.startTime AND a.endTime) OR " +
            "(a.startTime BETWEEN :startTime AND :endTime))")
    List<Appointment> findAppointmentBetweenTwoDates(@Param("startTime") LocalDateTime startTime,
                                                     @Param("endTime") LocalDateTime endTime);
    @Query("SELECT a FROM Appointment a WHERE " +
            "a.startTime >= :dateTime AND " +
            "a.doctor.id = :doctorId")
    List<Appointment> findUpcomingAppointmentByDoctor(@Param("doctorId") Long doctorId,
                                                      @Param("dateTime") LocalDateTime dateTime);
    @Query("SELECT a FROM Appointment a WHERE " +
            "a.startTime >= :dateTime AND " +
            "a.patient.id = :patientId")
    List<Appointment> findUpcomingAppointmentByPatient(@Param("patientId") Long patientId,
                                                       @Param("dateTime") LocalDateTime dateTime);



    boolean existsByPatientIdAndStartTime(Long patientId, LocalDateTime startTime);
    boolean existsByDoctorIdAndStartTime(Long doctorId, LocalDateTime startTime);
    List<Appointment> findByPatientIdAndStartTimeBetween(Long patientId, LocalDateTime start, LocalDateTime end);
    List<Appointment> findByDoctorIdAndStatusIn(Long doctorId, List<AppointmentStatus> statuses);
    List<Appointment> findByDoctor(Doctor doctor);
    List<Appointment> findByPatient(Patient patient);
    List<Appointment> findByStatus(AppointmentStatus status);

    @Query("SELECT a FROM Appointment a WHERE " +
            "(:query IS NULL OR LOWER(a.doctor.clinicName) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(a.patient.name) LIKE LOWER(CONCAT('%', :query, '%'))) " +
            "AND (:statuses IS NULL OR a.status IN :statuses) " +
            "AND (:startTime IS NULL OR a.startTime >= :startTime) " +
            "AND (:endTime IS NULL OR a.endTime <= :endTime)")
    List<Appointment> searchAppointments(@Param("query") String query,
                                         @Param("statuses") List<AppointmentStatus> statuses,
                                         @Param("startTime") LocalDateTime startTime,
                                         @Param("endTime") LocalDateTime endTime);

}
