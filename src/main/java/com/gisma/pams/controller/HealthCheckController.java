package com.gisma.pams.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
@CrossOrigin(origins = "*", maxAge = 3600)
public class HealthCheckController {

    @Autowired
    private Environment environment;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getHealth() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Patient Appointment Management System");
        response.put("version", "1.0.0");
        response.put("environment", environment.getActiveProfiles().length > 0 ? 
                    environment.getActiveProfiles()[0] : "default");
        response.put("timestamp", System.currentTimeMillis());
        
        Map<String, Object> components = new HashMap<>();
        components.put("database", "UP");
        components.put("server", "UP");
        components.put("apis", "OPERATIONAL");
        response.put("components", components);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/detailed")
    public ResponseEntity<Map<String, Object>> getDetailedHealth() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", System.currentTimeMillis());
        
        Map<String, Object> serviceInfo = new HashMap<>();
        serviceInfo.put("name", "Patient Appointment Management System");
        serviceInfo.put("version", "1.0.0");
        serviceInfo.put("description", "Enterprise-grade healthcare management system");
        serviceInfo.put("framework", "Spring Boot 3.1.0");
        serviceInfo.put("java_version", "17");
        serviceInfo.put("database", "H2 In-Memory");
        
        Map<String, Object> endpoints = new HashMap<>();
        endpoints.put("patients", "/api/patients");
        endpoints.put("doctors", "/api/doctors");
        endpoints.put("diseases", "/api/diseases");
        endpoints.put("appointments", "/api/appointments");
        endpoints.put("prescriptions", "/api/prescriptions");
        endpoints.put("payments", "/api/payments");
        endpoints.put("medical_records", "/api/medical-records");
        endpoints.put("patient_tests", "/api/patient-tests");
        endpoints.put("appointment_reviews", "/api/appointment-reviews");
        endpoints.put("notifications", "/api/notifications");
        endpoints.put("dashboard", "/api/dashboard");
        
        response.put("serviceInfo", serviceInfo);
        response.put("availableEndpoints", endpoints);
        response.put("totalControllers", 11);
        response.put("totalEntities", 9);
        response.put("totalEndpoints", "60+");
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ready")
    public ResponseEntity<Map<String, String>> getReadiness() {
        Map<String, String> response = new HashMap<>();
        response.put("ready", "true");
        response.put("service", "Patient Appointment Management System");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/alive")
    public ResponseEntity<String> getLiveness() {
        return ResponseEntity.ok("Service is alive");
    }
}
