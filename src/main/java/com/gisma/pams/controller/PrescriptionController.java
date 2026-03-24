package com.gisma.pams.controller;

import com.gisma.pams.dto.PrescriptionDTO;
import com.gisma.pams.model.Prescription;
import com.gisma.pams.service.PrescriptionService;
import com.gisma.pams.util.ApiResponse;
import com.gisma.pams.util.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/prescriptions")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final DTOMapper dtoMapper;

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService, DTOMapper dtoMapper) {
        this.prescriptionService = prescriptionService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createPrescription(@RequestBody Prescription prescription) {
        try {
            Prescription created = prescriptionService.createPrescription(prescription);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Prescription created successfully");
            response.put("data", dtoMapper.mapPrescriptionToDTO(created));
            response.put("status", "success");
            response.put("statusCode", 201);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error creating prescription: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 400);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getPrescriptionById(@PathVariable Long id) {
        try {
            Prescription prescription = prescriptionService.getPrescriptionById(id);
            if (prescription != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Prescription retrieved successfully");
                response.put("data", dtoMapper.mapPrescriptionToDTO(prescription));
                response.put("status", "success");
                response.put("statusCode", 200);
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Prescription not found");
                response.put("status", "error");
                response.put("statusCode", 404);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving prescription: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPrescriptions() {
        try {
            List<Prescription> prescriptions = prescriptionService.getAllPrescriptions();
            List<PrescriptionDTO> dtos = prescriptions.stream()
                    .map(dtoMapper::mapPrescriptionToDTO)
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Prescriptions retrieved successfully");
            response.put("data", dtos);
            response.put("status", "success");
            response.put("statusCode", 200);
            response.put("total", dtos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving prescriptions: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<Map<String, Object>> getPrescriptionsByAppointment(@PathVariable Long appointmentId) {
        try {
            List<Prescription> prescriptions = prescriptionService.getPrescriptionsByAppointmentId(appointmentId);
            List<PrescriptionDTO> dtos = prescriptions.stream()
                    .map(dtoMapper::mapPrescriptionToDTO)
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Appointment prescriptions retrieved successfully");
            response.put("data", dtos);
            response.put("status", "success");
            response.put("statusCode", 200);
            response.put("total", dtos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving prescriptions: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<Map<String, Object>> getPrescriptionsByDoctor(@PathVariable Long doctorId) {
        try {
            List<Prescription> prescriptions = prescriptionService.getPrescriptionsByDoctorId(doctorId);
            List<PrescriptionDTO> dtos = prescriptions.stream()
                    .map(dtoMapper::mapPrescriptionToDTO)
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Doctor prescriptions retrieved successfully");
            response.put("data", dtos);
            response.put("status", "success");
            response.put("statusCode", 200);
            response.put("total", dtos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving prescriptions: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<Map<String, Object>> getPrescriptionsByPatient(@PathVariable Long patientId) {
        try {
            List<Prescription> prescriptions = prescriptionService.getPrescriptionsByPatientId(patientId);
            List<PrescriptionDTO> dtos = prescriptions.stream()
                    .map(dtoMapper::mapPrescriptionToDTO)
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Patient prescriptions retrieved successfully");
            response.put("data", dtos);
            response.put("status", "success");
            response.put("statusCode", 200);
            response.put("total", dtos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving prescriptions: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/medicine/{medicineName}")
    public ResponseEntity<Map<String, Object>> getPrescriptionsByMedicine(@PathVariable String medicineName) {
        try {
            List<Prescription> prescriptions = prescriptionService.getPrescriptionsByMedicineName(medicineName);
            List<PrescriptionDTO> dtos = prescriptions.stream()
                    .map(dtoMapper::mapPrescriptionToDTO)
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Prescriptions retrieved successfully");
            response.put("data", dtos);
            response.put("status", "success");
            response.put("statusCode", 200);
            response.put("total", dtos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving prescriptions: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updatePrescription(@PathVariable Long id, @RequestBody Prescription prescription) {
        try {
            Prescription updated = prescriptionService.updatePrescription(id, prescription);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Prescription updated successfully");
            response.put("data", dtoMapper.mapPrescriptionToDTO(updated));
            response.put("status", "success");
            response.put("statusCode", 200);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error updating prescription: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 400);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletePrescription(@PathVariable Long id) {
        try {
            prescriptionService.deletePrescription(id);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Prescription deleted successfully");
            response.put("status", "success");
            response.put("statusCode", 200);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error deleting prescription: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 400);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
