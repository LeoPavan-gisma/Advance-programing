package com.gisma.pams.controller;

import com.gisma.pams.dto.MedicalRecordDTO;
import com.gisma.pams.model.MedicalRecord;
import com.gisma.pams.service.MedicalRecordService;
import com.gisma.pams.util.ApiResponse;
import com.gisma.pams.util.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/medical-records")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;
    private final DTOMapper dtoMapper;

    @Autowired
    public MedicalRecordController(MedicalRecordService medicalRecordService, DTOMapper dtoMapper) {
        this.medicalRecordService = medicalRecordService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        try {
            MedicalRecord created = medicalRecordService.createMedicalRecord(medicalRecord);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Medical record created successfully");
            response.put("data", dtoMapper.mapMedicalRecordToDTO(created));
            response.put("status", "success");
            response.put("statusCode", 201);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error creating medical record: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 400);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getMedicalRecordById(@PathVariable Long id) {
        try {
            MedicalRecord record = medicalRecordService.getMedicalRecordById(id);
            if (record != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Medical record retrieved successfully");
                response.put("data", dtoMapper.mapMedicalRecordToDTO(record));
                response.put("status", "success");
                response.put("statusCode", 200);
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Medical record not found");
                response.put("status", "error");
                response.put("statusCode", 404);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving medical record: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllMedicalRecords() {
        try {
            List<MedicalRecord> records = medicalRecordService.getAllMedicalRecords();
            List<MedicalRecordDTO> dtos = records.stream()
                    .map(dtoMapper::mapMedicalRecordToDTO)
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Medical records retrieved successfully");
            response.put("data", dtos);
            response.put("status", "success");
            response.put("statusCode", 200);
            response.put("total", dtos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving medical records: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/patient/{patientId}/history")
    public ResponseEntity<Map<String, Object>> getPatientMedicalHistory(@PathVariable Long patientId) {
        try {
            List<MedicalRecord> records = medicalRecordService.getPatientMedicalHistory(patientId);
            List<MedicalRecordDTO> dtos = records.stream()
                    .map(dtoMapper::mapMedicalRecordToDTO)
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Patient medical history retrieved successfully");
            response.put("data", dtos);
            response.put("status", "success");
            response.put("statusCode", 200);
            response.put("total", dtos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving medical history: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<Map<String, Object>> getRecordsByDoctor(@PathVariable Long doctorId) {
        try {
            List<MedicalRecord> records = medicalRecordService.getDoctorRecords(doctorId);
            List<MedicalRecordDTO> dtos = records.stream()
                    .map(dtoMapper::mapMedicalRecordToDTO)
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Doctor records retrieved successfully");
            response.put("data", dtos);
            response.put("status", "success");
            response.put("statusCode", 200);
            response.put("total", dtos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving records: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/type/{recordType}")
    public ResponseEntity<Map<String, Object>> getRecordsByType(@PathVariable String recordType) {
        try {
            List<MedicalRecord> records = medicalRecordService.getRecordsByType(recordType);
            List<MedicalRecordDTO> dtos = records.stream()
                    .map(dtoMapper::mapMedicalRecordToDTO)
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Records retrieved successfully");
            response.put("data", dtos);
            response.put("status", "success");
            response.put("statusCode", 200);
            response.put("total", dtos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving records: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Map<String, Object>> getRecordsByStatus(@PathVariable String status) {
        try {
            List<MedicalRecord> records = medicalRecordService.getRecordsByStatus(status);
            List<MedicalRecordDTO> dtos = records.stream()
                    .map(dtoMapper::mapMedicalRecordToDTO)
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Records retrieved successfully");
            response.put("data", dtos);
            response.put("status", "success");
            response.put("statusCode", 200);
            response.put("total", dtos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving records: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateMedicalRecord(@PathVariable Long id, @RequestBody MedicalRecord medicalRecord) {
        try {
            MedicalRecord updated = medicalRecordService.updateMedicalRecord(id, medicalRecord);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Medical record updated successfully");
            response.put("data", dtoMapper.mapMedicalRecordToDTO(updated));
            response.put("status", "success");
            response.put("statusCode", 200);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error updating medical record: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 400);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteMedicalRecord(@PathVariable Long id) {
        try {
            medicalRecordService.deleteMedicalRecord(id);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Medical record deleted successfully");
            response.put("status", "success");
            response.put("statusCode", 200);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error deleting medical record: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 400);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
