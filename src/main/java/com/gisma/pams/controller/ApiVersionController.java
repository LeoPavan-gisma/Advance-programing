package com.gisma.pams.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ApiVersionController {

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getApiInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("apiVersion", "1.0");
        response.put("status", "ACTIVE");
        response.put("releaseDate", "2026-03-19");
        response.put("description", "Patient Appointment Management System API v1.0");
        response.put("supportedEndpoints", getSupportedEndpoints());
        return ResponseEntity.ok(response);
    }

    private Map<String, String> getSupportedEndpoints() {
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("patients", "/api/patients");
        endpoints.put("doctors", "/api/doctors");
        endpoints.put("diseases", "/api/diseases");
        endpoints.put("appointments", "/api/appointments");
        endpoints.put("prescriptions", "/api/prescriptions");
        endpoints.put("payments", "/api/payments");
        endpoints.put("medical_records", "/api/medical-records");
        endpoints.put("patient_tests", "/api/patient-tests");
        endpoints.put("reviews", "/api/appointment-reviews");
        endpoints.put("notifications", "/api/notifications");
        endpoints.put("dashboard", "/api/dashboard");
        endpoints.put("health", "/api/health");
        return endpoints;
    }
}
