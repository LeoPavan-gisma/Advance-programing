package com.gisma.pams.service;

import com.gisma.pams.dto.DashboardStatsDTO;
import com.gisma.pams.model.Appointment;
import com.gisma.pams.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;

@Service
@Transactional(readOnly = true)
public class DashboardService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientTestRepository patientTestRepository;
    private final PaymentRepository paymentRepository;
    private final AppointmentReviewRepository appointmentReviewRepository;

    @Autowired
    public DashboardService(PatientRepository patientRepository,
                            DoctorRepository doctorRepository,
                            AppointmentRepository appointmentRepository,
                            MedicalRecordRepository medicalRecordRepository,
                            PatientTestRepository patientTestRepository,
                            PaymentRepository paymentRepository,
                            AppointmentReviewRepository appointmentReviewRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.patientTestRepository = patientTestRepository;
        this.paymentRepository = paymentRepository;
        this.appointmentReviewRepository = appointmentReviewRepository;
    }

    public DashboardStatsDTO getDashboardStats() {
        DashboardStatsDTO stats = new DashboardStatsDTO();

        stats.setTotalPatients(patientRepository.count());
        stats.setTotalDoctors(doctorRepository.count());
        stats.setTotalAppointments(appointmentRepository.count());
        stats.setScheduledAppointments((long) appointmentRepository.findByStatus(Appointment.AppointmentStatus.SCHEDULED).size());
        stats.setCompletedAppointments((long) appointmentRepository.findByStatus(Appointment.AppointmentStatus.COMPLETED).size());
        stats.setCancelledAppointments((long) appointmentRepository.findByStatus(Appointment.AppointmentStatus.CANCELLED).size());
        stats.setTotalMedicalRecords(medicalRecordRepository.count());
        stats.setTotalTests(patientTestRepository.count());

        // Calculate revenue for current month
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime firstDay = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime lastDay = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59);
        BigDecimal monthlyRevenue = paymentRepository.getTotalRevenueInPeriod(firstDay, lastDay);
        stats.setTotalRevenue(monthlyRevenue != null ? monthlyRevenue : BigDecimal.ZERO);

        // Calculate average doctor rating
        Double avgRating = appointmentRepository.findAll().stream()
                .mapToDouble(a -> {
                    Double rating = appointmentReviewRepository.getAverageDoctorRating(a.getDoctor().getDoctorId());
                    return rating != null ? rating : 0.0;
                })
                .average()
                .orElse(0.0);
        stats.setAverageRating(avgRating);

        return stats;
    }

    public DashboardStatsDTO getDashboardStatsByMonth(int year, int month) {
        DashboardStatsDTO stats = new DashboardStatsDTO();

        LocalDateTime firstDay = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime lastDay = firstDay.withDayOfMonth(firstDay.toLocalDate().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59);

        stats.setTotalAppointments((long) appointmentRepository.findAppointmentsBetweenDates(firstDay, lastDay).size());
        stats.setScheduledAppointments((long) appointmentRepository.findAppointmentsBetweenDates(firstDay, lastDay).stream()
                .filter(a -> a.getStatus() == Appointment.AppointmentStatus.SCHEDULED).count());
        stats.setCompletedAppointments((long) appointmentRepository.findAppointmentsBetweenDates(firstDay, lastDay).stream()
                .filter(a -> a.getStatus() == Appointment.AppointmentStatus.COMPLETED).count());
        stats.setCancelledAppointments((long) appointmentRepository.findAppointmentsBetweenDates(firstDay, lastDay).stream()
                .filter(a -> a.getStatus() == Appointment.AppointmentStatus.CANCELLED).count());

        BigDecimal monthlyRevenue = paymentRepository.getTotalRevenueInPeriod(firstDay, lastDay);
        stats.setTotalRevenue(monthlyRevenue != null ? monthlyRevenue : BigDecimal.ZERO);

        stats.setTotalPatients(patientRepository.count());
        stats.setTotalDoctors(doctorRepository.count());
        stats.setTotalMedicalRecords(medicalRecordRepository.count());
        stats.setTotalTests(patientTestRepository.count());

        return stats;
    }
}
