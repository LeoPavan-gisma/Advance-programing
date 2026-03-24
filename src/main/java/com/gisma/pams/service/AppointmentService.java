package com.gisma.pams.service;

import com.gisma.pams.exception.AppointmentConflictException;
import com.gisma.pams.exception.AppointmentNotFoundException;
import com.gisma.pams.model.Appointment;
import com.gisma.pams.model.Doctor;
import com.gisma.pams.model.Patient;
import com.gisma.pams.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository,
                             PatientService patientService,
                             DoctorService doctorService) {
        this.appointmentRepository = appointmentRepository;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    public Appointment createAppointment(Appointment appointment) {
        validateAppointmentData(appointment);
        checkForDoctorConflict(appointment.getDoctor().getDoctorId(), appointment.getAppointmentDateTime());
        checkForPatientConflict(appointment.getPatient().getPatientId(), appointment.getAppointmentDateTime());
        return appointmentRepository.save(appointment);
    }

    public Appointment getAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID: " + appointmentId));
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment updateAppointment(Long appointmentId, Appointment appointmentDetails) {
        Appointment existingAppointment = getAppointmentById(appointmentId);

        if (appointmentDetails.getAppointmentDateTime() != null) {
            if (!existingAppointment.getAppointmentDateTime().equals(appointmentDetails.getAppointmentDateTime())) {
                checkForDoctorConflict(existingAppointment.getDoctor().getDoctorId(), appointmentDetails.getAppointmentDateTime());
                checkForPatientConflict(existingAppointment.getPatient().getPatientId(), appointmentDetails.getAppointmentDateTime());
            }
            existingAppointment.setAppointmentDateTime(appointmentDetails.getAppointmentDateTime());
        }

        if (appointmentDetails.getStatus() != null) {
            existingAppointment.setStatus(appointmentDetails.getStatus());
        }

        if (appointmentDetails.getReason() != null) {
            existingAppointment.setReason(appointmentDetails.getReason());
        }

        if (appointmentDetails.getNotes() != null) {
            existingAppointment.setNotes(appointmentDetails.getNotes());
        }

        return appointmentRepository.save(existingAppointment);
    }

    public void deleteAppointment(Long appointmentId) {
        Appointment appointment = getAppointmentById(appointmentId);
        appointmentRepository.delete(appointment);
    }

    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        patientService.getPatientById(patientId);
        return appointmentRepository.findByPatientPatientId(patientId);
    }

    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        doctorService.getDoctorById(doctorId);
        return appointmentRepository.findByDoctorDoctorId(doctorId);
    }

    public List<Appointment> getAppointmentsByDisease(Long diseaseId) {
        return appointmentRepository.findByDiseaseDiseaseId(diseaseId);
    }

    public List<Appointment> getAppointmentsByStatus(Appointment.AppointmentStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        return appointmentRepository.findByStatus(status);
    }

    public List<Appointment> getAppointmentsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        return appointmentRepository.findAppointmentsBetweenDates(startDate, endDate);
    }

    public List<Appointment> getPatientAppointmentsByStatus(Long patientId, Appointment.AppointmentStatus status) {
        patientService.getPatientById(patientId);
        return appointmentRepository.findByPatientAndStatus(patientId, status);
    }

    public List<Appointment> getDoctorAppointmentsByStatus(Long doctorId, Appointment.AppointmentStatus status) {
        doctorService.getDoctorById(doctorId);
        return appointmentRepository.findByDoctorAndStatus(doctorId, status);
    }

    public List<Appointment> getDoctorAppointmentsBetweenDates(Long doctorId, LocalDateTime startDate, LocalDateTime endDate) {
        doctorService.getDoctorById(doctorId);
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        return appointmentRepository.findDoctorAppointmentsBetweenDates(doctorId, startDate, endDate);
    }

    public void cancelAppointment(Long appointmentId) {
        Appointment appointment = getAppointmentById(appointmentId);
        if (appointment.getStatus() == Appointment.AppointmentStatus.CANCELLED) {
            throw new IllegalArgumentException("Appointment is already cancelled");
        }
        appointment.setStatus(Appointment.AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }

    public void completeAppointment(Long appointmentId) {
        Appointment appointment = getAppointmentById(appointmentId);
        if (appointment.getStatus() == Appointment.AppointmentStatus.COMPLETED) {
            throw new IllegalArgumentException("Appointment is already completed");
        }
        appointment.setStatus(Appointment.AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);
    }

    public void rescheduleAppointment(Long appointmentId, LocalDateTime newDateTime) {
        Appointment appointment = getAppointmentById(appointmentId);
        checkForDoctorConflict(appointment.getDoctor().getDoctorId(), newDateTime);
        checkForPatientConflict(appointment.getPatient().getPatientId(), newDateTime);
        appointment.setAppointmentDateTime(newDateTime);
        appointment.setStatus(Appointment.AppointmentStatus.RESCHEDULED);
        appointmentRepository.save(appointment);
    }

    private void validateAppointmentData(Appointment appointment) {
        if (appointment.getPatient() == null || appointment.getPatient().getPatientId() == null) {
            throw new IllegalArgumentException("Patient is required");
        }
        if (appointment.getDoctor() == null || appointment.getDoctor().getDoctorId() == null) {
            throw new IllegalArgumentException("Doctor is required");
        }
        if (appointment.getDisease() == null || appointment.getDisease().getDiseaseId() == null) {
            throw new IllegalArgumentException("Disease is required");
        }
        if (appointment.getAppointmentDateTime() == null) {
            throw new IllegalArgumentException("Appointment date and time is required");
        }
        if (appointment.getAppointmentDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Appointment date must be in the future");
        }
    }

    private void checkForDoctorConflict(Long doctorId, LocalDateTime appointmentDateTime) {
        LocalDateTime startWindow = appointmentDateTime.minusHours(1);
        LocalDateTime endWindow = appointmentDateTime.plusHours(1);
        
        List<Appointment> conflictingAppointments = appointmentRepository
                .findDoctorAppointmentsBetweenDates(doctorId, startWindow, endWindow)
                .stream()
                .filter(a -> a.getStatus() != Appointment.AppointmentStatus.CANCELLED)
                .toList();

        if (!conflictingAppointments.isEmpty()) {
            throw new AppointmentConflictException("Doctor has conflicting appointment at this time");
        }
    }

    private void checkForPatientConflict(Long patientId, LocalDateTime appointmentDateTime) {
        LocalDateTime startWindow = appointmentDateTime.minusHours(2);
        LocalDateTime endWindow = appointmentDateTime.plusHours(2);
        
        List<Appointment> conflictingAppointments = appointmentRepository
                .findAppointmentsBetweenDates(startWindow, endWindow)
                .stream()
                .filter(a -> a.getPatient().getPatientId().equals(patientId))
                .filter(a -> a.getStatus() != Appointment.AppointmentStatus.CANCELLED)
                .toList();

        if (!conflictingAppointments.isEmpty()) {
            throw new AppointmentConflictException("Patient has conflicting appointment at this time");
        }
    }

    public long getTotalAppointmentsCount() {
        return appointmentRepository.count();
    }

    public long getScheduledAppointmentsCount() {
        return appointmentRepository.findByStatus(Appointment.AppointmentStatus.SCHEDULED).size();
    }

    public long getCompletedAppointmentsCount() {
        return appointmentRepository.findByStatus(Appointment.AppointmentStatus.COMPLETED).size();
    }

    public long getCancelledAppointmentsCount() {
        return appointmentRepository.findByStatus(Appointment.AppointmentStatus.CANCELLED).size();
    }
}
