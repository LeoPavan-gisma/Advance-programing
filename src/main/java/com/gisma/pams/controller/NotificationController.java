package com.gisma.pams.controller;

import com.gisma.pams.dto.NotificationDTO;
import com.gisma.pams.model.Notification;
import com.gisma.pams.service.NotificationService;
import com.gisma.pams.util.ApiResponse;
import com.gisma.pams.util.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*", maxAge = 3600)
public class NotificationController {

    private final NotificationService notificationService;
    private final DTOMapper dtoMapper;

    @Autowired
    public NotificationController(NotificationService notificationService, DTOMapper dtoMapper) {
        this.notificationService = notificationService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> sendNotification(@RequestBody Notification notification) {
        try {
            Notification sent = notificationService.sendNotification(notification);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Notification sent successfully");
            response.put("data", dtoMapper.mapNotificationToDTO(sent));
            response.put("status", "success");
            response.put("statusCode", 201);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error sending notification: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 400);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getNotificationById(@PathVariable Long id) {
        try {
            Notification notification = notificationService.getNotificationById(id);
            if (notification != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Notification retrieved successfully");
                response.put("data", dtoMapper.mapNotificationToDTO(notification));
                response.put("status", "success");
                response.put("statusCode", 200);
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Notification not found");
                response.put("status", "error");
                response.put("statusCode", 404);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving notification: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllNotifications() {
        try {
            List<Notification> notifications = notificationService.getAllNotifications();
            List<NotificationDTO> dtos = notifications.stream()
                    .map(dtoMapper::mapNotificationToDTO)
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Notifications retrieved successfully");
            response.put("data", dtos);
            response.put("status", "success");
            response.put("statusCode", 200);
            response.put("total", dtos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving notifications: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<Map<String, Object>> getPatientNotifications(@PathVariable Long patientId) {
        try {
            List<Notification> notifications = notificationService.getPatientNotifications(patientId);
            List<NotificationDTO> dtos = notifications.stream()
                    .map(dtoMapper::mapNotificationToDTO)
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Patient notifications retrieved successfully");
            response.put("data", dtos);
            response.put("status", "success");
            response.put("statusCode", 200);
            response.put("total", dtos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving notifications: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<Map<String, Object>> getDoctorNotifications(@PathVariable Long doctorId) {
        try {
            List<Notification> notifications = notificationService.getDoctorNotifications(doctorId);
            List<NotificationDTO> dtos = notifications.stream()
                    .map(dtoMapper::mapNotificationToDTO)
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Doctor notifications retrieved successfully");
            response.put("data", dtos);
            response.put("status", "success");
            response.put("statusCode", 200);
            response.put("total", dtos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving notifications: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/patient/{patientId}/unread")
    public ResponseEntity<Map<String, Object>> getUnreadPatientNotifications(@PathVariable Long patientId) {
        try {
            List<Notification> notifications = notificationService.getUnreadPatientNotifications(patientId);
            List<NotificationDTO> dtos = notifications.stream()
                    .map(dtoMapper::mapNotificationToDTO)
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Unread notifications retrieved successfully");
            response.put("data", dtos);
            response.put("status", "success");
            response.put("statusCode", 200);
            response.put("total", dtos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving notifications: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/patient/{patientId}/unread-count")
    public ResponseEntity<Map<String, Object>> getUnreadNotificationCount(@PathVariable Long patientId) {
        try {
            Long count = notificationService.getUnreadNotificationCount(patientId);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Unread count retrieved successfully");
            response.put("data", count);
            response.put("status", "success");
            response.put("statusCode", 200);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving count: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Map<String, Object>> markAsRead(@PathVariable Long id) {
        try {
            Notification updated = notificationService.markAsRead(id);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Notification marked as read");
            response.put("data", dtoMapper.mapNotificationToDTO(updated));
            response.put("status", "success");
            response.put("statusCode", 200);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error marking notification: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 400);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/patient/{patientId}/read-all")
    public ResponseEntity<Map<String, Object>> markAllAsRead(@PathVariable Long patientId) {
        try {
            notificationService.markAllAsRead(patientId);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "All notifications marked as read");
            response.put("status", "success");
            response.put("statusCode", 200);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error marking notifications: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 400);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteNotification(@PathVariable Long id) {
        try {
            notificationService.deleteNotification(id);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Notification deleted successfully");
            response.put("status", "success");
            response.put("statusCode", 200);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error deleting notification: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 400);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
