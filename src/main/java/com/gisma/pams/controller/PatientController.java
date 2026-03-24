package com.gisma.pams.controller;

import com.gisma.pams.model.Patient;
import com.gisma.pams.service.PatientService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createPatient(@RequestBody @Valid Patient patient) {
        Patient createdPatient = patientService.createPatient(patient);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Patient created successfully");
        response.put("data", createdPatient);
        response.put("status", "success");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<Map<String, Object>> getPatient(@PathVariable Long patientId) {
        Patient patient = patientService.getPatientById(patientId);
        Map<String, Object> response = new HashMap<>();
        response.put("data", patient);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        Map<String, Object> response = new HashMap<>();
        response.put("data", patients);
        response.put("total", patients.size());
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{patientId}")
    public ResponseEntity<Map<String, Object>> updatePatient(
            @PathVariable Long patientId,
            @RequestBody @Valid Patient patientDetails) {
        Patient updatedPatient = patientService.updatePatient(patientId, patientDetails);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Patient updated successfully");
        response.put("data", updatedPatient);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<Map<String, Object>> deletePatient(@PathVariable Long patientId) {
        patientService.deletePatient(patientId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Patient deleted successfully");
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<Map<String, Object>> getPatientByPhone(@PathVariable String phoneNumber) {
        Patient patient = patientService.getPatientByPhoneNumber(phoneNumber);
        Map<String, Object> response = new HashMap<>();
        response.put("data", patient);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Map<String, Object>> getPatientByEmail(@PathVariable String email) {
        Patient patient = patientService.getPatientByEmail(email);
        Map<String, Object> response = new HashMap<>();
        response.put("data", patient);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchPatients(@RequestParam String name) {
        List<Patient> patients = patientService.searchPatientsByName(name);
        Map<String, Object> response = new HashMap<>();
        response.put("data", patients);
        response.put("total", patients.size());
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/firstname/{firstName}")
    public ResponseEntity<Map<String, Object>> getPatientsByFirstName(@PathVariable String firstName) {
        List<Patient> patients = patientService.getPatientsByFirstName(firstName);
        Map<String, Object> response = new HashMap<>();
        response.put("data", patients);
        response.put("total", patients.size());
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/lastname/{lastName}")
    public ResponseEntity<Map<String, Object>> getPatientsByLastName(@PathVariable String lastName) {
        List<Patient> patients = patientService.getPatientsByLastName(lastName);
        Map<String, Object> response = new HashMap<>();
        response.put("data", patients);
        response.put("total", patients.size());
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/exists/phone/{phoneNumber}")
    public ResponseEntity<Map<String, Object>> checkPhoneExists(@PathVariable String phoneNumber) {
        boolean exists = patientService.existsByPhoneNumber(phoneNumber);
        Map<String, Object> response = new HashMap<>();
        response.put("exists", exists);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Map<String, Object>> checkEmailExists(@PathVariable String email) {
        boolean exists = patientService.existsByEmail(email);
        Map<String, Object> response = new HashMap<>();
        response.put("exists", exists);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }
}
