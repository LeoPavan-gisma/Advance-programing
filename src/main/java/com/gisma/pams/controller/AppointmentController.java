package com.gisma.pams.controller;

import com.gisma.pams.model.Appointment;
import com.gisma.pams.service.AppointmentService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createAppointment(
            @RequestBody @Valid Appointment appointment) {
        Appointment createdAppointment = appointmentService.createAppointment(appointment);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Appointment scheduled successfully");
        response.put("data", createdAppointment);
        response.put("status", "success");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<Map<String, Object>> getAppointment(@PathVariable Long appointmentId) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        Map<String, Object> response = new HashMap<>();
        response.put("data", appointment);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        Map<String, Object> response = new HashMap<>();
        response.put("data", appointments);
        response.put("total", appointments.size());
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<Map<String, Object>> updateAppointment(
            @PathVariable Long appointmentId,
            @RequestBody @Valid Appointment appointmentDetails) {
        Appointment updatedAppointment = appointmentService.updateAppointment(appointmentId, appointmentDetails);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Appointment updated successfully");
        response.put("data", updatedAppointment);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<Map<String, Object>> deleteAppointment(@PathVariable Long appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Appointment deleted successfully");
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<Map<String, Object>> getAppointmentsByPatient(@PathVariable Long patientId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patientId);
        Map<String, Object> response = new HashMap<>();
        response.put("data", appointments);
        response.put("total", appointments.size());
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<Map<String, Object>> getAppointmentsByDoctor(@PathVariable Long doctorId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(doctorId);
        Map<String, Object> response = new HashMap<>();
        response.put("data", appointments);
        response.put("total", appointments.size());
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/disease/{diseaseId}")
    public ResponseEntity<Map<String, Object>> getAppointmentsByDisease(@PathVariable Long diseaseId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDisease(diseaseId);
        Map<String, Object> response = new HashMap<>();
        response.put("data", appointments);
        response.put("total", appointments.size());
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Map<String, Object>> getAppointmentsByStatus(@PathVariable String status) {
        Appointment.AppointmentStatus appointmentStatus = Appointment.AppointmentStatus.valueOf(status.toUpperCase());
        List<Appointment> appointments = appointmentService.getAppointmentsByStatus(appointmentStatus);
        Map<String, Object> response = new HashMap<>();
        response.put("data", appointments);
        response.put("total", appointments.size());
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/date-range")
    public ResponseEntity<Map<String, Object>> getAppointmentsBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Appointment> appointments = appointmentService.getAppointmentsBetweenDates(startDate, endDate);
        Map<String, Object> response = new HashMap<>();
        response.put("data", appointments);
        response.put("total", appointments.size());
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient/{patientId}/status/{status}")
    public ResponseEntity<Map<String, Object>> getPatientAppointmentsByStatus(
            @PathVariable Long patientId,
            @PathVariable String status) {
        Appointment.AppointmentStatus appointmentStatus = Appointment.AppointmentStatus.valueOf(status.toUpperCase());
        List<Appointment> appointments = appointmentService.getPatientAppointmentsByStatus(patientId, appointmentStatus);
        Map<String, Object> response = new HashMap<>();
        response.put("data", appointments);
        response.put("total", appointments.size());
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/doctor/{doctorId}/status/{status}")
    public ResponseEntity<Map<String, Object>> getDoctorAppointmentsByStatus(
            @PathVariable Long doctorId,
            @PathVariable String status) {
        Appointment.AppointmentStatus appointmentStatus = Appointment.AppointmentStatus.valueOf(status.toUpperCase());
        List<Appointment> appointments = appointmentService.getDoctorAppointmentsByStatus(doctorId, appointmentStatus);
        Map<String, Object> response = new HashMap<>();
        response.put("data", appointments);
        response.put("total", appointments.size());
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/doctor/{doctorId}/date-range")
    public ResponseEntity<Map<String, Object>> getDoctorAppointmentsBetweenDates(
            @PathVariable Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Appointment> appointments = appointmentService.getDoctorAppointmentsBetweenDates(doctorId, startDate, endDate);
        Map<String, Object> response = new HashMap<>();
        response.put("data", appointments);
        response.put("total", appointments.size());
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{appointmentId}/cancel")
    public ResponseEntity<Map<String, Object>> cancelAppointment(@PathVariable Long appointmentId) {
        appointmentService.cancelAppointment(appointmentId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Appointment cancelled successfully");
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{appointmentId}/complete")
    public ResponseEntity<Map<String, Object>> completeAppointment(@PathVariable Long appointmentId) {
        appointmentService.completeAppointment(appointmentId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Appointment completed successfully");
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{appointmentId}/reschedule")
    public ResponseEntity<Map<String, Object>> rescheduleAppointment(
            @PathVariable Long appointmentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime newDateTime) {
        appointmentService.rescheduleAppointment(appointmentId, newDateTime);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Appointment rescheduled successfully");
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/stats/total")
    public ResponseEntity<Map<String, Object>> getTotalAppointmentsCount() {
        long count = appointmentService.getTotalAppointmentsCount();
        Map<String, Object> response = new HashMap<>();
        response.put("total", count);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/stats/scheduled")
    public ResponseEntity<Map<String, Object>> getScheduledAppointmentsCount() {
        long count = appointmentService.getScheduledAppointmentsCount();
        Map<String, Object> response = new HashMap<>();
        response.put("scheduled", count);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/stats/completed")
    public ResponseEntity<Map<String, Object>> getCompletedAppointmentsCount() {
        long count = appointmentService.getCompletedAppointmentsCount();
        Map<String, Object> response = new HashMap<>();
        response.put("completed", count);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/stats/cancelled")
    public ResponseEntity<Map<String, Object>> getCancelledAppointmentsCount() {
        long count = appointmentService.getCancelledAppointmentsCount();
        Map<String, Object> response = new HashMap<>();
        response.put("cancelled", count);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getAllStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", appointmentService.getTotalAppointmentsCount());
        stats.put("scheduled", appointmentService.getScheduledAppointmentsCount());
        stats.put("completed", appointmentService.getCompletedAppointmentsCount());
        stats.put("cancelled", appointmentService.getCancelledAppointmentsCount());

        Map<String, Object> response = new HashMap<>();
        response.put("data", stats);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }
}
