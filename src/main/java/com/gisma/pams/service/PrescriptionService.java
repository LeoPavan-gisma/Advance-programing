package com.gisma.pams.service;

import com.gisma.pams.exception.PatientNotFoundException;
import com.gisma.pams.model.Prescription;
import com.gisma.pams.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;

    @Autowired
    public PrescriptionService(PrescriptionRepository prescriptionRepository,
                               AppointmentService appointmentService,
                               DoctorService doctorService) {
        this.prescriptionRepository = prescriptionRepository;
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
    }

    public Prescription createPrescription(Prescription prescription) {
        validatePrescription(prescription);
        return prescriptionRepository.save(prescription);
    }

    public Prescription getPrescriptionById(Long prescriptionId) {
        return prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new PatientNotFoundException("Prescription not found with ID: " + prescriptionId));
    }

    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    public Prescription updatePrescription(Long prescriptionId, Prescription prescriptionDetails) {
        Prescription existingPrescription = getPrescriptionById(prescriptionId);

        if (prescriptionDetails.getMedicineName() != null && !prescriptionDetails.getMedicineName().isBlank()) {
            existingPrescription.setMedicineName(prescriptionDetails.getMedicineName());
        }
        if (prescriptionDetails.getDosage() != null && prescriptionDetails.getDosage() > 0) {
            existingPrescription.setDosage(prescriptionDetails.getDosage());
        }
        if (prescriptionDetails.getUnit() != null && !prescriptionDetails.getUnit().isBlank()) {
            existingPrescription.setUnit(prescriptionDetails.getUnit());
        }
        if (prescriptionDetails.getFrequency() != null && !prescriptionDetails.getFrequency().isBlank()) {
            existingPrescription.setFrequency(prescriptionDetails.getFrequency());
        }
        if (prescriptionDetails.getDurationDays() != null && prescriptionDetails.getDurationDays() > 0) {
            existingPrescription.setDurationDays(prescriptionDetails.getDurationDays());
        }
        if (prescriptionDetails.getNotes() != null) {
            existingPrescription.setNotes(prescriptionDetails.getNotes());
        }
        if (prescriptionDetails.getSideEffects() != null) {
            existingPrescription.setSideEffects(prescriptionDetails.getSideEffects());
        }

        return prescriptionRepository.save(existingPrescription);
    }

    public void deletePrescription(Long prescriptionId) {
        Prescription prescription = getPrescriptionById(prescriptionId);
        prescriptionRepository.delete(prescription);
    }

    public List<Prescription> getAppointmentPrescriptions(Long appointmentId) {
        appointmentService.getAppointmentById(appointmentId);
        return prescriptionRepository.findByAppointmentAppointmentId(appointmentId);
    }

    public List<Prescription> getDoctorPrescriptions(Long doctorId) {
        doctorService.getDoctorById(doctorId);
        return prescriptionRepository.findByDoctorDoctorId(doctorId);
    }

    public List<Prescription> getPatientPrescriptions(Long patientId) {
        return prescriptionRepository.findByPatientId(patientId);
    }

    // Method aliases to match controller expectations
    public List<Prescription> getPrescriptionsByAppointmentId(Long appointmentId) {
        return getAppointmentPrescriptions(appointmentId);
    }

    public List<Prescription> getPrescriptionsByDoctorId(Long doctorId) {
        return getDoctorPrescriptions(doctorId);
    }

    public List<Prescription> getPrescriptionsByPatientId(Long patientId) {
        return getPatientPrescriptions(patientId);
    }

    public List<Prescription> getPrescriptionsByMedicineName(String medicineName) {
        return prescriptionRepository.findByMedicineName(medicineName);
    }

    private void validatePrescription(Prescription prescription) {
        if (prescription.getMedicineName() == null || prescription.getMedicineName().isBlank()) {
            throw new IllegalArgumentException("Medicine name is required");
        }
        if (prescription.getDosage() == null || prescription.getDosage() <= 0) {
            throw new IllegalArgumentException("Dosage must be positive");
        }
        if (prescription.getFrequency() == null || prescription.getFrequency().isBlank()) {
            throw new IllegalArgumentException("Frequency is required");
        }
        if (prescription.getAppointment() == null || prescription.getAppointment().getAppointmentId() == null) {
            throw new IllegalArgumentException("Appointment is required");
        }
        if (prescription.getDoctor() == null || prescription.getDoctor().getDoctorId() == null) {
            throw new IllegalArgumentException("Doctor is required");
        }
    }

    public long getTotalPrescriptionsCount() {
        return prescriptionRepository.count();
    }
}
