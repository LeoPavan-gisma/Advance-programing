package com.gisma.pams.service;

import com.gisma.pams.exception.DoctorNotFoundException;
import com.gisma.pams.model.Doctor;
import com.gisma.pams.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DoctorService {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor createDoctor(Doctor doctor) {
        validateDoctorData(doctor);
        return doctorRepository.save(doctor);
    }

    public Doctor getDoctorById(Long doctorId) {
        return doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with ID: " + doctorId));
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor updateDoctor(Long doctorId, Doctor doctorDetails) {
        Doctor existingDoctor = getDoctorById(doctorId);

        if (doctorDetails.getFirstName() != null && !doctorDetails.getFirstName().isBlank()) {
            existingDoctor.setFirstName(doctorDetails.getFirstName());
        }
        if (doctorDetails.getLastName() != null && !doctorDetails.getLastName().isBlank()) {
            existingDoctor.setLastName(doctorDetails.getLastName());
        }
        if (doctorDetails.getSpecialty() != null && !doctorDetails.getSpecialty().isBlank()) {
            existingDoctor.setSpecialty(doctorDetails.getSpecialty());
        }
        if (doctorDetails.getDepartment() != null && !doctorDetails.getDepartment().isBlank()) {
            existingDoctor.setDepartment(doctorDetails.getDepartment());
        }
        if (doctorDetails.getPhoneNumber() != null && !doctorDetails.getPhoneNumber().isBlank()) {
            existingDoctor.setPhoneNumber(doctorDetails.getPhoneNumber());
        }
        if (doctorDetails.getEmail() != null && !doctorDetails.getEmail().isBlank()) {
            existingDoctor.setEmail(doctorDetails.getEmail());
        }
        if (doctorDetails.getQualifications() != null) {
            existingDoctor.setQualifications(doctorDetails.getQualifications());
        }

        return doctorRepository.save(existingDoctor);
    }

    public void deleteDoctor(Long doctorId) {
        Doctor doctor = getDoctorById(doctorId);
        doctorRepository.delete(doctor);
    }

    public Doctor getDoctorByPhoneNumber(String phoneNumber) {
        return doctorRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with phone: " + phoneNumber));
    }

    public Doctor getDoctorByEmail(String email) {
        return doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with email: " + email));
    }

    public List<Doctor> getDoctorsBySpecialty(String specialty) {
        if (specialty == null || specialty.isBlank()) {
            throw new IllegalArgumentException("Specialty cannot be empty");
        }
        return doctorRepository.findBySpecialty(specialty);
    }

    public List<Doctor> getDoctorsByDepartment(String department) {
        if (department == null || department.isBlank()) {
            throw new IllegalArgumentException("Department cannot be empty");
        }
        return doctorRepository.findByDepartment(department);
    }

    public List<Doctor> searchDoctorsByName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Search term cannot be empty");
        }
        return doctorRepository.searchByName(name);
    }

    public List<Doctor> getDoctorsBySpecialtyAndDepartment(String specialty, String department) {
        if (specialty == null || specialty.isBlank() || department == null || department.isBlank()) {
            throw new IllegalArgumentException("Specialty and department cannot be empty");
        }
        return doctorRepository.findBySpecialtyAndDepartment(specialty, department);
    }

    private void validateDoctorData(Doctor doctor) {
        if (doctor.getFirstName() == null || doctor.getFirstName().isBlank()) {
            throw new IllegalArgumentException("Doctor first name is required");
        }
        if (doctor.getLastName() == null || doctor.getLastName().isBlank()) {
            throw new IllegalArgumentException("Doctor last name is required");
        }
        if (doctor.getSpecialty() == null || doctor.getSpecialty().isBlank()) {
            throw new IllegalArgumentException("Specialty is required");
        }
        if (doctor.getDepartment() == null || doctor.getDepartment().isBlank()) {
            throw new IllegalArgumentException("Department is required");
        }
        if (doctor.getPhoneNumber() == null || doctor.getPhoneNumber().isBlank()) {
            throw new IllegalArgumentException("Phone number is required");
        }
        if (!isValidPhoneNumber(doctor.getPhoneNumber())) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
        if (doctor.getEmail() != null && !doctor.getEmail().isBlank()) {
            if (!isValidEmail(doctor.getEmail())) {
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
        return doctorRepository.findByPhoneNumber(phoneNumber).isPresent();
    }

    public boolean existsByEmail(String email) {
        return doctorRepository.findByEmail(email).isPresent();
    }

    public int getTotalDoctorsCount() {
        return (int) doctorRepository.count();
    }
}
