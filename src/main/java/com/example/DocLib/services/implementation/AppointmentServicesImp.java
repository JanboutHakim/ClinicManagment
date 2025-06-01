package com.example.DocLib.services.implementation;

import com.example.DocLib.dto.appointment.AppointmentDto;
import com.example.DocLib.dto.appointment.AppointmentNotificationDto;
import com.example.DocLib.dto.doctor.TimeBlock;
import com.example.DocLib.enums.AppointmentStatus;
import com.example.DocLib.models.appointment.Appointment;
import com.example.DocLib.models.doctor.Doctor;
import com.example.DocLib.models.doctor.DoctorHolidaySchedule;
import com.example.DocLib.models.doctor.DoctorSchedule;
import com.example.DocLib.models.patient.Patient;
import com.example.DocLib.repositories.AppointmentRepository;
import com.example.DocLib.services.interfaces.AppointmentServices;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppointmentServicesImp implements AppointmentServices {

    private final ModelMapper modelMapper;
    private final AppointmentRepository appointmentRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final DoctorServicesImp doctorServicesImp;
    private final PatientServicesImp patientServicesImp;
    // e.g., notify patients 10 minutes before the appointment
    private static final long REMINDER_THRESHOLD_MINUTES = 10;


    @Autowired
    public AppointmentServicesImp(ModelMapper modelMapper, AppointmentRepository appointmentRepository, SimpMessagingTemplate messagingTemplate, DoctorServicesImp doctorServicesImp, PatientServicesImp patientServicesImp) {
        this.modelMapper = modelMapper;
        this.appointmentRepository = appointmentRepository;
        this.messagingTemplate = messagingTemplate;
        this.doctorServicesImp = doctorServicesImp;
        this.patientServicesImp = patientServicesImp;
    }

    @Override
    @Transactional
    @CacheEvict(value = "availableSlots", key = "#appointmentDto.doctorId"+"-"+"#appointmentDto.startTime")
    public AppointmentDto createAppointment(AppointmentDto appointmentDto) {
        Appointment appointment = new Appointment();
        appointmentDto.setStatus(AppointmentStatus.ON_HOLD);
        if(doctorServicesImp.getDoctorById(appointmentDto.getDoctorId()) != null &&
            patientServicesImp.getPatientById(appointmentDto.getPatientId())!= null)
               appointment= appointmentRepository.save(modelMapper.map(appointmentDto,Appointment.class));
        return convertAppointmentToDto(appointment);
    }

    @Override
    public AppointmentDto getAppointmentById(Long appointmentId) {
        return convertAppointmentToDto(getAppointmentEntity(appointmentId));
    }

    @Override
    @Transactional
    public void deleteAppointment(Long appointmentId) {
            appointmentRepository.deleteById(appointmentId);
    }

    @Override
    @Transactional
    @CacheEvict(value = "availableSlots", key = "#appointmentDto.doctorId + '-' + #appointmentDto.startTime")
    public AppointmentDto rescheduleAppointment(Long appointmentId, AppointmentDto appointmentDto) {
        Appointment appointment = getAppointmentEntity(appointmentId);

        // Get the doctor checkup duration
        LocalDateTime newEndTime = appointmentDto.getStartTime().plusMinutes(doctorServicesImp.getDoctorById(appointmentDto.getDoctorId()).getCheckupDuration());

        boolean available = isDoctorAvailable(appointment.getDoctor().getId(), appointmentDto.getStartTime());
        if (!available) {
            throw new IllegalStateException("Doctor is not available at the requested time.");
        }

        modelMapper.map(appointment,appointmentDto);
        appointment.setStatus(AppointmentStatus.RESCHEDULED);
        appointment.setUpdatedAt(LocalDateTime.now());

        return convertAppointmentToDto(appointment);
    }



    @Override
    @Transactional
    @CacheEvict(value = "availableSlots", key = "#appointmentId")
    public AppointmentDto cancelAppointmentByClinic(Long appointmentId, String cancellationReason) {
        Appointment appointment = getAppointmentEntity(appointmentId);
        appointment.setCancellationReason(cancellationReason);
        appointment.setStatus(AppointmentStatus.CANCELLED_BY_CLINIC);
        return convertAppointmentToDto(appointment);
    }
    @Override
    @Transactional
    @CacheEvict(value = "availableSlots", key = "#appointmentId")
    public AppointmentDto cancelAppointmentByPatient(Long appointmentId, String cancellationReason) {
        Appointment appointment = getAppointmentEntity(appointmentId);
        appointment.setCancellationReason(cancellationReason);
        appointment.setStatus(AppointmentStatus.CANCELLED_BY_PATIENT);
        return convertAppointmentToDto(appointment);
    }



    @Override
    @Transactional
    public AppointmentDto confirmAppointment(Long appointmentId) {
        Appointment appointment = getAppointmentEntity(appointmentId);
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        return convertAppointmentToDto(appointment);
    }

    @Override
    public AppointmentDto updateAppointmentStatus(Long appointmentId, AppointmentStatus status) {
        return null;
    }


    @Override
    public List<AppointmentDto> getAppointmentsByDoctor(Long doctorId) {
        return convertAppointmentListToDtoList(appointmentRepository.findByDoctor(modelMapper.map(doctorServicesImp.getDoctorById(doctorId),Doctor.class)));
    }

    @Override
    public List<AppointmentDto> getAppointmentsByPatient(Long patientId) {
        return convertAppointmentListToDtoList(appointmentRepository.findByPatient(modelMapper.map(patientServicesImp.getPatientById(patientId),Patient.class)));
    }
    @Override
    public List<AppointmentDto> getAppointmentsByDateRange(LocalDateTime start, LocalDateTime end) {
        return convertAppointmentListToDtoList(appointmentRepository.findAppointmentBetweenTwoDates(start,end));
    }

    @Override
    public List<AppointmentDto> getUpcomingAppointmentsForDoctor(Long doctorId) {
        return convertAppointmentListToDtoList(appointmentRepository.findUpcomingAppointmentByDoctor(doctorId,LocalDateTime.now()));
    }

    @Override
    public List<AppointmentDto> getUpcomingAppointmentsForPatient(Long patientId) {
        return convertAppointmentListToDtoList(appointmentRepository.findUpcomingAppointmentByDoctor(patientId,LocalDateTime.now()));
    }

    @Override
    public boolean isDoctorAvailable(Long doctorId, LocalDateTime startTime) {
        LocalDateTime endTime = startTime.plusMinutes(doctorServicesImp.getDoctorById(doctorId).getCheckupDuration()); // default duration or based on config

        List<Appointment> existingAppointments = appointmentRepository.findOverlappingAppointments(
                doctorId, startTime, endTime, List.of(AppointmentStatus.CONFIRMED, AppointmentStatus.ON_HOLD)
        );

        return existingAppointments.isEmpty();
    }
    @Override
    public boolean isDoctorOnVacation(Long doctorId, LocalDateTime startTime){
        Doctor doctor = doctorServicesImp.getDoctorEntity(doctorId);
        List<DoctorHolidaySchedule> doctorHolidayScheduleList = doctor.getHolidaySchedules();

        for(DoctorHolidaySchedule doctorHolidaySchedule:doctorHolidayScheduleList){
            if(doctorHolidaySchedule.getStartTime().equals(LocalTime.from(startTime))&&
                doctorHolidaySchedule.getHolidayDate().equals(LocalDate.from(startTime)) ){
                return false;
            }
        }
        return true;
    }

    @Override
    @Cacheable(value = "availableSlots", key = "#doctorId")
    public List<LocalDateTime> getDoctorAvailableSlots(Long doctorId) {
        Map<DayOfWeek, List<TimeBlock>> doctorSchedule = getDoctorWeeklySchedule(doctorId);
        List<LocalDateTime> availableSlots = new ArrayList<>();
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusMonths(1);
        int checkupDuration = getCachedCheckupDuration(doctorId);
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            DayOfWeek day = date.getDayOfWeek();
            if (doctorSchedule.containsKey(day)) {
                for (TimeBlock block : doctorSchedule.get(day)) {
                    LocalDateTime slotStart = LocalDateTime.of(date, block.getStart());
                    while (slotStart.plusMinutes(checkupDuration).isBefore(LocalDateTime.of(date, block.getEnd()))) {
                        if (isDoctorAvailable(doctorId, slotStart)&& isDoctorOnVacation(doctorId,slotStart)) {
                            availableSlots.add(slotStart);
                        }
                        slotStart = slotStart.plusMinutes(30);
                    }
                }
            }
        }
        return availableSlots;
    }

    public Map<DayOfWeek, List<TimeBlock>> getDoctorWeeklySchedule(Long doctorId) {
        List<DoctorSchedule> scheduleEntries = doctorServicesImp.getSchedules(doctorId)
                .stream()
                .map(dto -> modelMapper.map(dto, DoctorSchedule.class))
                .toList();
        Map<DayOfWeek, List<TimeBlock>> scheduleMap = new HashMap<>();
        for (DoctorSchedule entry : scheduleEntries) {
            scheduleMap
                    .computeIfAbsent(entry.getDayOfWeek(), k -> new ArrayList<>())
                    .add(new TimeBlock(entry.getStartTime(), entry.getEndTime()));
        }

        return scheduleMap;
    }




    @Override
    public List<AppointmentDto> getAppointmentsByStatus(AppointmentStatus status) {
        return List.of();
    }

    @Scheduled(fixedRate = 60000) // Every 60 seconds
    public void checkUpcomingAppointments() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime targetTime = now.plusMinutes(REMINDER_THRESHOLD_MINUTES);

        List<Appointment> upcomingAppointments =
                appointmentRepository.findByStartTimeBetween(now, targetTime);

        for (Appointment appointment : upcomingAppointments) {
            Patient patient = appointment.getPatient();

            // Create your custom message DTO
            AppointmentNotificationDto notification = new AppointmentNotificationDto(
                    "Reminder: You have an appointment at " + appointment.getStartTime()
            );

            messagingTemplate.convertAndSendToUser(
                    patient.getUser().getUsername(),
                    "/queue/appointments",
                    notification
            );
        }
    }

    @Override
    public void sendAppointmentReminders() {

    }

    @Override
    public List<AppointmentDto> getCancelledAppointments(Long doctorId) {
        List<Appointment> cancelledAppointments = appointmentRepository
                .findByDoctorIdAndStatusIn(
                        doctorId,
                        List.of(AppointmentStatus.CANCELLED_BY_CLINIC, AppointmentStatus.CANCELLED_BY_PATIENT)
                );
        return cancelledAppointments.stream()
                .map(this::convertAppointmentToDto)
                .toList();
    }



    @Override
    public int getAppointmentCountByStatus(AppointmentStatus status) {
        return appointmentRepository.countByStatus(status);
    }


    @Override
    public double getDoctorCancellationRate(Long doctorId) {
        int total = appointmentRepository.countByDoctorId(doctorId);
        if (total == 0) return 0.0;

        int cancelled = appointmentRepository.countByDoctorIdAndStatusIn(
                doctorId,
                List.of(AppointmentStatus.CANCELLED_BY_CLINIC, AppointmentStatus.CANCELLED_BY_PATIENT)
        );
        return (double) cancelled / total;
    }


    @Override
    public List<AppointmentDto> getPatientHistory(Long patientId, int monthsBack) {
        LocalDateTime fromDate = LocalDateTime.now().minusMonths(monthsBack);

        List<Appointment> historyAppointments = appointmentRepository
                .findByPatientIdAndStartTimeBetween(patientId, fromDate, LocalDateTime.now());

        return historyAppointments.stream()
                .map(this::convertAppointmentToDto)
                .toList();
    }

    private AppointmentDto convertAppointmentToDto(Appointment appointment){
        return modelMapper.map(appointment,AppointmentDto.class);
    }

    private List<AppointmentDto>  convertAppointmentListToDtoList(List<Appointment> appointmentList){
        return appointmentList.stream()
                .map(this::convertAppointmentToDto)
                .toList();
    }

    private Appointment getAppointmentEntity(Long appointmentId){
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment with ID " + appointmentId + " not found"));
    }
    @Cacheable(value = "checkupDuration", key = "#doctorId")
    private int getCachedCheckupDuration(Long doctorId) {
        return doctorServicesImp.getDoctorById(doctorId).getCheckupDuration();
    }



}
