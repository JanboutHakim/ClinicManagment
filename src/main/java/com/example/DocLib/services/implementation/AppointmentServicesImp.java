package com.example.DocLib.services.implementation;

import com.example.DocLib.dto.appointment.AppointmentDto;
import com.example.DocLib.dto.appointment.AppointmentNotificationDto;
import com.example.DocLib.dto.doctor.TimeBlock;
import com.example.DocLib.enums.AppointmentStatus;
import com.example.DocLib.exceptions.custom.AppointmentNotAvailableAtThisTime;
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
import java.util.*;

@Service
public class AppointmentServicesImp implements AppointmentServices {

    private final ModelMapper modelMapper;
    private final AppointmentRepository appointmentRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final DoctorServicesImp doctorServicesImp;
    private final PatientServicesImp patientServicesImp;
    private final UserServicesImp userServicesImp;
    private static final long REMINDER_THRESHOLD_MINUTES = 60;

    @Autowired
    public AppointmentServicesImp(ModelMapper modelMapper, AppointmentRepository appointmentRepository, SimpMessagingTemplate messagingTemplate, DoctorServicesImp doctorServicesImp, PatientServicesImp patientServicesImp, UserServicesImp userServicesImp) {
        this.modelMapper = modelMapper;
        this.appointmentRepository = appointmentRepository;
        this.messagingTemplate = messagingTemplate;
        this.doctorServicesImp = doctorServicesImp;
        this.patientServicesImp = patientServicesImp;
        this.userServicesImp = userServicesImp;
    }

    @Override
    @Transactional
    @CacheEvict(value = "availableSlots", key = "#appointmentDto.doctorId + '-' + #appointmentDto.startTime")
    public AppointmentDto addAppointment(AppointmentDto appointmentDto) {
        appointmentDto.setStatus(AppointmentStatus.ON_HOLD);

        if (!isDoctorAvailable(appointmentDto.getDoctorId(), appointmentDto.getStartTime()) ||
                isDoctorOnVacation(appointmentDto.getDoctorId(), appointmentDto.getStartTime())) {
            throw new AppointmentNotAvailableAtThisTime("Doctor is not available at the requested time.");
        } else if (!isPatientAvailable(appointmentDto.getPatientId(), appointmentDto.getStartTime())) {
            throw new AppointmentNotAvailableAtThisTime("Patient is not available at the requested time.");
        }

        Appointment appointment = appointmentRepository.save(modelMapper.map(appointmentDto, Appointment.class));
        messagingTemplate.convertAndSendToUser(
                userServicesImp.getUser(appointmentDto.getDoctorId()).getUsername(), // must match authenticated username
                "/queue/appointments",appointmentDto);
        messagingTemplate.convertAndSendToUser(
                userServicesImp.getUser(appointmentDto.getDoctorId()).getUsername(),
                "/queue/appointments",
                Map.of("content", "New appointment added!")
        );
        System.out.println(userServicesImp.getUser(appointmentDto.getDoctorId()).getUsername());
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
        LocalDateTime newEndTime = appointmentDto.getStartTime()
                .plusMinutes(getCachedCheckupDuration(appointmentDto.getDoctorId()));

        if (!isDoctorAvailable(appointmentDto.getDoctorId(), appointmentDto.getStartTime()) ||
                isDoctorOnVacation(appointmentDto.getDoctorId(), appointmentDto.getStartTime())) {
            throw new AppointmentNotAvailableAtThisTime("Doctor is not available at the requested time.");
        } else if (!isPatientAvailable(appointmentDto.getPatientId(), appointmentDto.getStartTime())) {
            throw new AppointmentNotAvailableAtThisTime("Patient is not available at the requested time.");
        }

        modelMapper.map(appointmentDto, appointment);
        appointment.setStatus(AppointmentStatus.RESCHEDULED);
        appointment.setUpdatedAt(LocalDateTime.now());
        appointment = appointmentRepository.save(appointment);

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
        Appointment appointment = getAppointmentEntity(appointmentId);
        appointment.setStatus(status);
        appointment.setUpdatedAt(LocalDateTime.now());
        appointment = appointmentRepository.save(appointment);
        return convertAppointmentToDto(appointment);
    }

    @Override
    public List<AppointmentDto> getAppointmentsByDoctor(Long doctorId) {
        return convertAppointmentListToDtoList(
                appointmentRepository.findByDoctor(modelMapper.map(doctorServicesImp.getDoctorById(doctorId), Doctor.class))
        );
    }

    @Override
    public List<AppointmentDto> getAppointmentsByPatient(Long patientId) {
        return convertAppointmentListToDtoList(
                appointmentRepository.findByPatient(modelMapper.map(patientServicesImp.getPatientById(patientId), Patient.class))
        );
    }

    @Override
    public List<AppointmentDto> getAppointmentsByDateRange(LocalDateTime start, LocalDateTime end) {
        return convertAppointmentListToDtoList(appointmentRepository.findAppointmentBetweenTwoDates(start, end));
    }

    @Override
    public List<AppointmentDto> getUpcomingAppointmentsForDoctor(Long doctorId) {
        return convertAppointmentListToDtoList(appointmentRepository.findUpcomingAppointmentByDoctor(doctorId, LocalDateTime.now()));
    }

    @Override
    public List<AppointmentDto> getUpcomingAppointmentsForPatient(Long patientId) {
        return convertAppointmentListToDtoList(appointmentRepository.findUpcomingAppointmentByPatient(patientId, LocalDateTime.now()));
    }

    @Override
    public boolean isDoctorAvailable(Long doctorId, LocalDateTime startTime) {
        LocalDateTime endTime = startTime.plusMinutes(getCachedCheckupDuration(doctorId));
        return appointmentRepository.findOverlappingAppointments(
                doctorId, startTime, endTime, List.of(AppointmentStatus.CONFIRMED, AppointmentStatus.ON_HOLD)
        ).isEmpty();
    }

    @Override
    public boolean isDoctorOnVacation(Long doctorId, LocalDateTime dateTime) {
        return !doctorServicesImp.findHolidayScheduleByDate(doctorId, dateTime).isEmpty();
    }

    @Override
    public boolean isPatientAvailable(Long patientId, LocalDateTime startTime) {
        return !appointmentRepository.existsByPatientIdAndStartTime(patientId, startTime);
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
                    while (!slotStart.plusMinutes(checkupDuration).isAfter(LocalDateTime.of(date, block.getEnd()))) {
                        if (isDoctorAvailable(doctorId, slotStart) && !isDoctorOnVacation(doctorId, slotStart)) {
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
        List<DoctorSchedule> scheduleEntries = doctorServicesImp.getSchedules(doctorId).stream()
                .map(dto -> modelMapper.map(dto, DoctorSchedule.class))
                .toList();

        Map<DayOfWeek, List<TimeBlock>> scheduleMap = new HashMap<>();
        for (DoctorSchedule entry : scheduleEntries) {
            scheduleMap.computeIfAbsent(entry.getDayOfWeek(), k -> new ArrayList<>())
                    .add(new TimeBlock(entry.getStartTime(), entry.getEndTime()));
        }
        return scheduleMap;
    }

    @Override
    public List<AppointmentDto> getAppointmentsByStatus(AppointmentStatus status) {
        return convertAppointmentListToDtoList(appointmentRepository.findByStatus(status));
    }

    @Scheduled(fixedRate = 60000)
    public void checkUpcomingAppointments() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime targetTime = now.plusMinutes(REMINDER_THRESHOLD_MINUTES);
        List<Appointment> upcomingAppointments = appointmentRepository.findByStartTimeBetween(now, targetTime);

        for (Appointment appointment : upcomingAppointments) {
            Patient patient = appointment.getPatient();
            AppointmentNotificationDto notification = new AppointmentNotificationDto(
                    "Reminder: You have an appointment at " + appointment.getStartTime(),
                    convertAppointmentToDto(appointment)
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
        // Placeholder if needed in the future
    }

    @Override
    public List<AppointmentDto> getCancelledAppointments(Long doctorId) {
        return convertAppointmentListToDtoList(
                appointmentRepository.findByDoctorIdAndStatusIn(
                        doctorId,
                        List.of(AppointmentStatus.CANCELLED_BY_CLINIC, AppointmentStatus.CANCELLED_BY_PATIENT)
                )
        );
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
        return convertAppointmentListToDtoList(
                appointmentRepository.findByPatientIdAndStartTimeBetween(patientId, fromDate, LocalDateTime.now())
        );
    }

    private AppointmentDto convertAppointmentToDto(Appointment appointment) {
        return modelMapper.map(appointment, AppointmentDto.class);
    }

    private List<AppointmentDto> convertAppointmentListToDtoList(List<Appointment> appointmentList) {
        return appointmentList.stream()
                .map(this::convertAppointmentToDto)
                .toList();
    }

    private Appointment getAppointmentEntity(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment with ID " + appointmentId + " not found"));
    }

    @Cacheable(value = "checkupDuration", key = "#doctorId")
    public int getCachedCheckupDuration(Long doctorId) {
        return doctorServicesImp.getDoctorById(doctorId).getCheckupDurationInMinutes();
    }
}
