package com.gisma.pams.util;

import com.gisma.pams.model.*;
import com.gisma.pams.dto.*;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class DTOMapper {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public PatientDTO mapPatientToDTO(Patient patient) {
        if (patient == null) return null;
        return new PatientDTO(
                patient.getPatientId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getAge(),
                patient.getPhoneNumber(),
                patient.getEmail(),
                patient.getMedicalHistory(),
                patient.getCreatedAt() != null ? patient.getCreatedAt() : "",
                patient.getUpdatedAt() != null ? patient.getUpdatedAt() : ""
        );
    }

    public DoctorDTO mapDoctorToDTO(Doctor doctor) {
        if (doctor == null) return null;
        return new DoctorDTO(
                doctor.getDoctorId(),
                doctor.getFirstName(),
                doctor.getLastName(),
                doctor.getSpecialty(),
                doctor.getDepartment(),
                doctor.getPhoneNumber(),
                doctor.getEmail(),
                doctor.getQualifications(),
                null,
                null,
                doctor.getCreatedAt() != null ? doctor.getCreatedAt() : "",
                doctor.getUpdatedAt() != null ? doctor.getUpdatedAt() : ""
        );
    }

    public DiseaseDTO mapDiseaseToDTO(Disease disease) {
        if (disease == null) return null;
        return new DiseaseDTO(
                disease.getDiseaseId(),
                disease.getDiseaseName(),
                disease.getDescription(),
                disease.getSymptoms(),
                disease.getTreatment(),
                disease.getCreatedAt() != null ? disease.getCreatedAt().toString() : "",
                disease.getUpdatedAt() != null ? disease.getUpdatedAt().toString() : ""
        );
    }

    public AppointmentDTO mapAppointmentToDTO(Appointment appointment) {
        if (appointment == null) return null;
        return new AppointmentDTO(
                appointment.getAppointmentId(),
                mapPatientToDTO(appointment.getPatient()),
                mapDoctorToDTO(appointment.getDoctor()),
                mapDiseaseToDTO(appointment.getDisease()),
                appointment.getAppointmentDateTime() != null ? appointment.getAppointmentDateTime().toString() : "",
                appointment.getReason(),
                appointment.getStatus() != null ? appointment.getStatus().toString() : "",
                appointment.getNotes(),
                appointment.getCreatedAt() != null ? appointment.getCreatedAt().toString() : "",
                appointment.getUpdatedAt() != null ? appointment.getUpdatedAt().toString() : ""
        );
    }

    public PaymentDTO mapPaymentToDTO(Payment payment) {
        if (payment == null) return null;
        return new PaymentDTO(
                payment.getPaymentId(),
                payment.getAppointment() != null ? payment.getAppointment().getAppointmentId() : null,
                payment.getPatient() != null ? payment.getPatient().getPatientId() : null,
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus() != null ? payment.getPaymentStatus().toString() : "",
                payment.getDescription(),
                payment.getTransactionId(),
                payment.getCreatedAt() != null ? payment.getCreatedAt().toString() : "",
                payment.getUpdatedAt() != null ? payment.getUpdatedAt().toString() : ""
        );
    }

    public PrescriptionDTO mapPrescriptionToDTO(Prescription prescription) {
        if (prescription == null) return null;
        return new PrescriptionDTO(
                prescription.getPrescriptionId(),
                prescription.getMedicineName(),
                prescription.getDosage(),
                prescription.getUnit(),
                prescription.getFrequency(),
                prescription.getDurationDays(),
                prescription.getNotes(),
                prescription.getSideEffects(),
                prescription.getAppointment() != null ? prescription.getAppointment().getAppointmentId() : null,
                prescription.getDoctor() != null ? prescription.getDoctor().getDoctorId() : null,
                prescription.getCreatedAt() != null ? prescription.getCreatedAt().toString() : "",
                prescription.getUpdatedAt() != null ? prescription.getUpdatedAt().toString() : ""
        );
    }

    public MedicalRecordDTO mapMedicalRecordToDTO(MedicalRecord record) {
        if (record == null) return null;
        return new MedicalRecordDTO(
                record.getRecordId(),
                record.getPatient() != null ? record.getPatient().getPatientId() : null,
                record.getAppointment() != null ? record.getAppointment().getAppointmentId() : null,
                record.getDoctor() != null ? record.getDoctor().getDoctorId() : null,
                record.getRecordType(),
                record.getDiagnosis(),
                record.getTreatmentPlan(),
                record.getClinicalNotes(),
                record.getAllergies(),
                record.getMedication(),
                record.getStatus(),
                record.getCreatedAt() != null ? record.getCreatedAt().toString() : "",
                record.getUpdatedAt() != null ? record.getUpdatedAt().toString() : ""
        );
    }

    public PatientTestDTO mapPatientTestToDTO(PatientTest test) {
        if (test == null) return null;
        return new PatientTestDTO(
                test.getTestId(),
                test.getPatient() != null ? test.getPatient().getPatientId() : null,
                test.getDoctor() != null ? test.getDoctor().getDoctorId() : null,
                test.getTestName(),
                test.getTestType(),
                test.getResult(),
                test.getRemarks(),
                test.getNormalRange(),
                test.getTestStatus(),
                test.getCreatedAt() != null ? test.getCreatedAt().toString() : "",
                test.getUpdatedAt() != null ? test.getUpdatedAt().toString() : ""
        );
    }

    public AppointmentReviewDTO mapAppointmentReviewToDTO(AppointmentReview review) {
        if (review == null) return null;
        return new AppointmentReviewDTO(
                review.getReviewId(),
                review.getAppointment() != null ? review.getAppointment().getAppointmentId() : null,
                review.getPatient() != null ? review.getPatient().getPatientId() : null,
                review.getRating(),
                review.getComment(),
                review.getRecommendations(),
                review.getCreatedAt() != null ? review.getCreatedAt().toString() : "",
                review.getUpdatedAt() != null ? review.getUpdatedAt().toString() : ""
        );
    }

    public NotificationDTO mapNotificationToDTO(Notification notification) {
        if (notification == null) return null;
        return new NotificationDTO(
                notification.getNotificationId(),
                notification.getPatient() != null ? notification.getPatient().getPatientId() : null,
                notification.getDoctor() != null ? notification.getDoctor().getDoctorId() : null,
                notification.getNotificationType(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getIsRead(),
                notification.getStatus(),
                notification.getCreatedAt() != null ? notification.getCreatedAt().toString() : "",
                notification.getUpdatedAt() != null ? notification.getUpdatedAt().toString() : ""
        );
    }
}
