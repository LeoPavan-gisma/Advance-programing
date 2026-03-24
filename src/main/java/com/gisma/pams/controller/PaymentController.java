package com.gisma.pams.controller;

import com.gisma.pams.dto.PaymentDTO;
import com.gisma.pams.model.Payment;
import com.gisma.pams.service.PaymentService;
import com.gisma.pams.util.ApiResponse;
import com.gisma.pams.util.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PaymentController {

    private final PaymentService paymentService;
    private final DTOMapper dtoMapper;

    @Autowired
    public PaymentController(PaymentService paymentService, DTOMapper dtoMapper) {
        this.paymentService = paymentService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> processPayment(@RequestBody Payment payment) {
        try {
            Payment processed = paymentService.processPayment(payment);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Payment processed successfully");
            response.put("data", dtoMapper.mapPaymentToDTO(processed));
            response.put("status", "success");
            response.put("statusCode", 201);
            response.put("transactionId", processed.getTransactionId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error processing payment: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 400);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getPaymentById(@PathVariable Long id) {
        try {
            Payment payment = paymentService.getPaymentById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Payment retrieved successfully");
            response.put("data", dtoMapper.mapPaymentToDTO(payment));
            response.put("status", "success");
            response.put("statusCode", 200);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving payment: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPayments() {
        try {
            List<Payment> payments = paymentService.getAllPayments();
            List<PaymentDTO> dtos = payments.stream()
                    .map(dtoMapper::mapPaymentToDTO)
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Payments retrieved successfully");
            response.put("data", dtos);
            response.put("status", "success");
            response.put("statusCode", 200);
            response.put("total", dtos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving payments: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<Map<String, Object>> getPaymentsByPatient(@PathVariable Long patientId) {
        try {
            List<Payment> payments = paymentService.getPaymentsByPatientId(patientId);
            List<PaymentDTO> dtos = payments.stream()
                    .map(dtoMapper::mapPaymentToDTO)
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Patient payments retrieved successfully");
            response.put("data", dtos);
            response.put("status", "success");
            response.put("statusCode", 200);
            response.put("total", dtos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving payments: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<Map<String, Object>> getPaymentsByAppointment(@PathVariable Long appointmentId) {
        try {
            List<Payment> payments = paymentService.getPaymentsByAppointmentId(appointmentId);
            List<PaymentDTO> dtos = payments.stream()
                    .map(dtoMapper::mapPaymentToDTO)
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Appointment payments retrieved successfully");
            response.put("data", dtos);
            response.put("status", "success");
            response.put("statusCode", 200);
            response.put("total", dtos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving payments: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Map<String, Object>> getPaymentsByStatus(@PathVariable String status) {
        try {
            Payment.PaymentStatus paymentStatus = Payment.PaymentStatus.valueOf(status.toUpperCase());
            List<Payment> payments = paymentService.getPaymentsByStatus(paymentStatus);
            List<PaymentDTO> dtos = payments.stream()
                    .map(dtoMapper::mapPaymentToDTO)
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Payments retrieved successfully");
            response.put("data", dtos);
            response.put("status", "success");
            response.put("statusCode", 200);
            response.put("total", dtos.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving payments: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/revenue")
    public ResponseEntity<Map<String, Object>> getTotalRevenue(@RequestParam(required = false) 
                                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                               @RequestParam(required = false) 
                                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            BigDecimal revenue;
            if (startDate != null && endDate != null) {
                revenue = paymentService.getTotalRevenueByPeriod(startDate, endDate);
            } else {
                revenue = paymentService.getTotalRevenue();
            }
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Total revenue retrieved successfully");
            response.put("data", revenue);
            response.put("status", "success");
            response.put("statusCode", 200);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error calculating revenue: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Map<String, Object>> completePayment(@PathVariable Long id) {
        try {
            Payment completed = paymentService.completePayment(id);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Payment completed successfully");
            response.put("data", dtoMapper.mapPaymentToDTO(completed));
            response.put("status", "success");
            response.put("statusCode", 200);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error completing payment: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 400);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}/refund")
    public ResponseEntity<Map<String, Object>> refundPayment(@PathVariable Long id) {
        try {
            Payment refunded = paymentService.refundPayment(id);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Payment refunded successfully");
            response.put("data", dtoMapper.mapPaymentToDTO(refunded));
            response.put("status", "success");
            response.put("statusCode", 200);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error refunding payment: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 400);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletePayment(@PathVariable Long id) {
        try {
            paymentService.deletePayment(id);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Payment deleted successfully");
            response.put("status", "success");
            response.put("statusCode", 200);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error deleting payment: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 400);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
