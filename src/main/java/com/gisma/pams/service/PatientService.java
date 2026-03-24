package com.gisma.pams.service;

import com.gisma.pams.exception.PatientNotFoundException;
import com.gisma.pams.model.Patient;
import com.gisma.pams.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient createPatient(Patient patient) {
        validatePatientData(patient);
        return patientRepository.save(patient);
    }

    public Patient getPatientById(Long patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + patientId));
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient updatePatient(Long patientId, Patient patientDetails) {
        Patient existingPatient = getPatientById(patientId);
        
        if (patientDetails.getFirstName() != null && !patientDetails.getFirstName().isBlank()) {
            existingPatient.setFirstName(patientDetails.getFirstName());
        }
        if (patientDetails.getLastName() != null && !patientDetails.getLastName().isBlank()) {
            existingPatient.setLastName(patientDetails.getLastName());
        }
        if (patientDetails.getAge() > 0) {
            existingPatient.setAge(patientDetails.getAge());
        }
        if (patientDetails.getPhoneNumber() != null && !patientDetails.getPhoneNumber().isBlank()) {
            existingPatient.setPhoneNumber(patientDetails.getPhoneNumber());
        }
        if (patientDetails.getEmail() != null && !patientDetails.getEmail().isBlank()) {
            existingPatient.setEmail(patientDetails.getEmail());
        }
        if (patientDetails.getMedicalHistory() != null) {
            existingPatient.setMedicalHistory(patientDetails.getMedicalHistory());
        }

        return patientRepository.save(existingPatient);
    }

    public void deletePatient(Long patientId) {
        Patient patient = getPatientById(patientId);
        patientRepository.delete(patient);
    }

    public Patient getPatientByPhoneNumber(String phoneNumber) {
        return patientRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with phone: " + phoneNumber));
    }

    public Patient getPatientByEmail(String email) {
        return patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with email: " + email));
    }

    public List<Patient> searchPatientsByName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Search term cannot be empty");
        }
        return patientRepository.searchByName(name);
    }

    public List<Patient> getPatientsByFirstName(String firstName) {
        return patientRepository.findByFirstNameIgnoreCase(firstName);
    }

    public List<Patient> getPatientsByLastName(String lastName) {
        return patientRepository.findByLastNameIgnoreCase(lastName);
    }

    private void validatePatientData(Patient patient) {
        if (patient.getFirstName() == null || patient.getFirstName().isBlank()) {
            throw new IllegalArgumentException("Patient first name is required");
        }
        if (patient.getLastName() == null || patient.getLastName().isBlank()) {
            throw new IllegalArgumentException("Patient last name is required");
        }
        if (patient.getAge() < 0) {
            throw new IllegalArgumentException("Patient age cannot be negative");
        }
        if (patient.getPhoneNumber() == null || patient.getPhoneNumber().isBlank()) {
            throw new IllegalArgumentException("Phone number is required");
        }
        if (!isValidPhoneNumber(patient.getPhoneNumber())) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
        if (patient.getEmail() != null && !patient.getEmail().isBlank()) {
            if (!isValidEmail(patient.getEmail())) {
                throw new IllegalArgumentException("Invalid email format");
            }
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10,15}");
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        return patientRepository.findByPhoneNumber(phoneNumber).isPresent();
    }

    public boolean existsByEmail(String email) {
        return patientRepository.findByEmail(email).isPresent();
    }
}
