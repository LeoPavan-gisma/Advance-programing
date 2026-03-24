package com.gisma.pams.controller;

import com.gisma.pams.dto.PatientTestDTO;
import com.gisma.pams.model.PatientTest;
import com.gisma.pams.service.PatientTestService;
import com.gisma.pams.util.ApiResponse;
import com.gisma.pams.util.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/patient-tests")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PatientTestController {

    private final PatientTestService patientTestService;
    private final DTOMapper dtoMapper;

    @Autowired
    public PatientTestController(PatientTestService patientTestService, DTOMapper dtoMapper) {
        this.patientTestService = patientTestService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createPatientTest(@RequestBody PatientTest patientTest) {
        try {
            PatientTest created = patientTestService.createPatientTest(patientTest);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Patient test created successfully");
            response.put("data", dtoMapper.mapPatientTestToDTO(created));
            response.put("status", "success");
            response.put("statusCode", 201);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error creating patient test: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 400);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getPatientTestById(@PathVariable Long id) {
        try {
            PatientTest test = patientTestService.getPatientTestById(id);
            if (test != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Patient test retrieved successfully");
                response.put("data", dtoMapper.mapPatientTestToDTO(test));
                response.put("status", "success");
                response.put("statusCode", 200);
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Patient test not found");
                response.put("status", "error");
                response.put("statusCode", 404);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving patient test: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPatientTests() {
        try {
            List<PatientTest> tests = patientTestService.getAllPatientTests();
            List<PatientTestDTO> dtos = tests.stream()
                    .map(dtoMapper::mapPatientTestToDTO)
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Patient tests retrieved successfully");
            response.put("data", dtos);
            response.put("status", "success");
            response.put("statusCode", 200);
            response.put("total", dtos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving patient tests: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/patient/{patientId}/history")
    public ResponseEntity<Map<String, Object>> getPatientTestHistory(@PathVariable Long patientId) {
        try {
            List<PatientTest> tests = patientTestService.getPatientTestHistory(patientId);
            List<PatientTestDTO> dtos = tests.stream()
                    .map(dtoMapper::mapPatientTestToDTO)
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Patient test history retrieved successfully");
            response.put("data", dtos);
            response.put("status", "success");
            response.put("statusCode", 200);
            response.put("total", dtos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving test history: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<Map<String, Object>> getTestsByPatient(@PathVariable Long patientId) {
        try {
            List<PatientTest> tests = patientTestService.getPatientTests(patientId);
            List<PatientTestDTO> dtos = tests.stream()
                    .map(dtoMapper::mapPatientTestToDTO)
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Patient tests retrieved successfully");
            response.put("data", dtos);
            response.put("status", "success");
            response.put("statusCode", 200);
            response.put("total", dtos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving tests: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<Map<String, Object>> getTestsByDoctor(@PathVariable Long doctorId) {
        try {
            List<PatientTest> tests = patientTestService.getDoctorTests(doctorId);
            List<PatientTestDTO> dtos = tests.stream()
                    .map(dtoMapper::mapPatientTestToDTO)
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Doctor tests retrieved successfully");
            response.put("data", dtos);
            response.put("status", "success");
            response.put("statusCode", 200);
            response.put("total", dtos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving tests: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/type/{testType}")
    public ResponseEntity<Map<String, Object>> getTestsByType(@PathVariable String testType) {
        try {
            List<PatientTest> tests = patientTestService.getTestsByType(testType);
            List<PatientTestDTO> dtos = tests.stream()
                    .map(dtoMapper::mapPatientTestToDTO)
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Tests retrieved successfully");
            response.put("data", dtos);
            response.put("status", "success");
            response.put("statusCode", 200);
            response.put("total", dtos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving tests: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Map<String, Object>> getTestsByStatus(@PathVariable String status) {
        try {
            List<PatientTest> tests = patientTestService.getTestsByStatus(status);
            List<PatientTestDTO> dtos = tests.stream()
                    .map(dtoMapper::mapPatientTestToDTO)
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Tests retrieved successfully");
            response.put("data", dtos);
            response.put("status", "success");
            response.put("statusCode", 200);
            response.put("total", dtos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving tests: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updatePatientTest(@PathVariable Long id, @RequestBody PatientTest patientTest) {
        try {
            PatientTest updated = patientTestService.updatePatientTest(id, patientTest);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Patient test updated successfully");
            response.put("data", dtoMapper.mapPatientTestToDTO(updated));
            response.put("status", "success");
            response.put("statusCode", 200);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error updating patient test: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 400);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletePatientTest(@PathVariable Long id) {
        try {
            patientTestService.deletePatientTest(id);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Patient test deleted successfully");
            response.put("status", "success");
            response.put("statusCode", 200);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error deleting patient test: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 400);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
