package com.gisma.pams.controller;

import com.gisma.pams.model.Doctor;
import com.gisma.pams.service.DoctorService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createDoctor(@RequestBody @Valid Doctor doctor) {
        Doctor createdDoctor = doctorService.createDoctor(doctor);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Doctor created successfully");
        response.put("data", createdDoctor);
        response.put("status", "success");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<Map<String, Object>> getDoctor(@PathVariable Long doctorId) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        Map<String, Object> response = new HashMap<>();
        response.put("data", doctor);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        Map<String, Object> response = new HashMap<>();
        response.put("data", doctors);
        response.put("total", doctors.size());
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{doctorId}")
    public ResponseEntity<Map<String, Object>> updateDoctor(
            @PathVariable Long doctorId,
            @RequestBody @Valid Doctor doctorDetails) {
        Doctor updatedDoctor = doctorService.updateDoctor(doctorId, doctorDetails);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Doctor updated successfully");
        response.put("data", updatedDoctor);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<Map<String, Object>> deleteDoctor(@PathVariable Long doctorId) {
        doctorService.deleteDoctor(doctorId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Doctor deleted successfully");
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<Map<String, Object>> getDoctorByPhone(@PathVariable String phoneNumber) {
        Doctor doctor = doctorService.getDoctorByPhoneNumber(phoneNumber);
        Map<String, Object> response = new HashMap<>();
        response.put("data", doctor);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Map<String, Object>> getDoctorByEmail(@PathVariable String email) {
        Doctor doctor = doctorService.getDoctorByEmail(email);
        Map<String, Object> response = new HashMap<>();
        response.put("data", doctor);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchDoctors(@RequestParam String name) {
        List<Doctor> doctors = doctorService.searchDoctorsByName(name);
        Map<String, Object> response = new HashMap<>();
        response.put("data", doctors);
        response.put("total", doctors.size());
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/specialty/{specialty}")
    public ResponseEntity<Map<String, Object>> getDoctorsBySpecialty(@PathVariable String specialty) {
        List<Doctor> doctors = doctorService.getDoctorsBySpecialty(specialty);
        Map<String, Object> response = new HashMap<>();
        response.put("data", doctors);
        response.put("total", doctors.size());
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<Map<String, Object>> getDoctorsByDepartment(@PathVariable String department) {
        List<Doctor> doctors = doctorService.getDoctorsByDepartment(department);
        Map<String, Object> response = new HashMap<>();
        response.put("data", doctors);
        response.put("total", doctors.size());
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter")
    public ResponseEntity<Map<String, Object>> getDoctorsBySpecialtyAndDepartment(
            @RequestParam String specialty,
            @RequestParam String department) {
        List<Doctor> doctors = doctorService.getDoctorsBySpecialtyAndDepartment(specialty, department);
        Map<String, Object> response = new HashMap<>();
        response.put("data", doctors);
        response.put("total", doctors.size());
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/exists/phone/{phoneNumber}")
    public ResponseEntity<Map<String, Object>> checkPhoneExists(@PathVariable String phoneNumber) {
        boolean exists = doctorService.existsByPhoneNumber(phoneNumber);
        Map<String, Object> response = new HashMap<>();
        response.put("exists", exists);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Map<String, Object>> checkEmailExists(@PathVariable String email) {
        boolean exists = doctorService.existsByEmail(email);
        Map<String, Object> response = new HashMap<>();
        response.put("exists", exists);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Object>> getTotalDoctorsCount() {
        int count = doctorService.getTotalDoctorsCount();
        Map<String, Object> response = new HashMap<>();
        response.put("total", count);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }
}
