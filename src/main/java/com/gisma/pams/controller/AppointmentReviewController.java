package com.gisma.pams.controller;

import com.gisma.pams.dto.AppointmentReviewDTO;
import com.gisma.pams.model.AppointmentReview;
import com.gisma.pams.service.AppointmentReviewService;
import com.gisma.pams.util.ApiResponse;
import com.gisma.pams.util.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appointment-reviews")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AppointmentReviewController {

    private final AppointmentReviewService appointmentReviewService;
    private final DTOMapper dtoMapper;

    @Autowired
    public AppointmentReviewController(AppointmentReviewService appointmentReviewService, DTOMapper dtoMapper) {
        this.appointmentReviewService = appointmentReviewService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createReview(@RequestBody AppointmentReview review) {
        try {
            AppointmentReview created = appointmentReviewService.createAppointmentReview(review);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Review created successfully");
            response.put("data", dtoMapper.mapAppointmentReviewToDTO(created));
            response.put("status", "success");
            response.put("statusCode", 201);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error creating review: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 400);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getReviewById(@PathVariable Long id) {
        try {
            AppointmentReview review = appointmentReviewService.getAppointmentReviewById(id);
            if (review != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Review retrieved successfully");
                response.put("data", dtoMapper.mapAppointmentReviewToDTO(review));
                response.put("status", "success");
                response.put("statusCode", 200);
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Review not found");
                response.put("status", "error");
                response.put("statusCode", 404);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving review: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllReviews() {
        try {
            List<AppointmentReview> reviews = appointmentReviewService.getAllAppointmentReviews();
            List<AppointmentReviewDTO> dtos = reviews.stream()
                    .map(dtoMapper::mapAppointmentReviewToDTO)
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Reviews retrieved successfully");
            response.put("data", dtos);
            response.put("status", "success");
            response.put("statusCode", 200);
            response.put("total", dtos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving reviews: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<Map<String, Object>> getReviewByAppointment(@PathVariable Long appointmentId) {
        try {
            AppointmentReview review = appointmentReviewService.getAppointmentReviewByAppointmentId(appointmentId);
            if (review != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Review retrieved successfully");
                response.put("data", dtoMapper.mapAppointmentReviewToDTO(review));
                response.put("status", "success");
                response.put("statusCode", 200);
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Review not found");
                response.put("status", "error");
                response.put("statusCode", 404);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving review: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<Map<String, Object>> getReviewsByPatient(@PathVariable Long patientId) {
        try {
            List<AppointmentReview> reviews = appointmentReviewService.getPatientReviews(patientId);
            List<AppointmentReviewDTO> dtos = reviews.stream()
                    .map(dtoMapper::mapAppointmentReviewToDTO)
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Patient reviews retrieved successfully");
            response.put("data", dtos);
            response.put("status", "success");
            response.put("statusCode", 200);
            response.put("total", dtos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving reviews: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/doctor/{doctorId}/ratings")
    public ResponseEntity<Map<String, Object>> getDoctorAverageRating(@PathVariable Long doctorId) {
        try {
            Double averageRating = appointmentReviewService.getDoctorAverageRating(doctorId);
            Long reviewCount = appointmentReviewService.getDoctorReviewCount(doctorId);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Doctor rating retrieved successfully");
            Map<String, Object> ratingData = new HashMap<>();
            ratingData.put("doctorId", doctorId);
            ratingData.put("averageRating", averageRating != null ? averageRating : 0.0);
            ratingData.put("reviewCount", reviewCount != null ? reviewCount : 0L);
            response.put("data", ratingData);
            response.put("status", "success");
            response.put("statusCode", 200);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving rating: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<Map<String, Object>> getReviewsByRating(@PathVariable Integer rating) {
        try {
            List<AppointmentReview> reviews = appointmentReviewService.getReviewsByRating(rating);
            List<AppointmentReviewDTO> dtos = reviews.stream()
                    .map(dtoMapper::mapAppointmentReviewToDTO)
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Reviews retrieved successfully");
            response.put("data", dtos);
            response.put("status", "success");
            response.put("statusCode", 200);
            response.put("total", dtos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving reviews: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateReview(@PathVariable Long id, @RequestBody AppointmentReview review) {
        try {
            AppointmentReview updated = appointmentReviewService.updateAppointmentReview(id, review);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Review updated successfully");
            response.put("data", dtoMapper.mapAppointmentReviewToDTO(updated));
            response.put("status", "success");
            response.put("statusCode", 200);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error updating review: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 400);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteReview(@PathVariable Long id) {
        try {
            appointmentReviewService.deleteAppointmentReview(id);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Review deleted successfully");
            response.put("status", "success");
            response.put("statusCode", 200);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error deleting review: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 400);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
