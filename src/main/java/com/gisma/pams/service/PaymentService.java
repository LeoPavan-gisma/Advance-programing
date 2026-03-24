package com.gisma.pams.service;

import com.gisma.pams.exception.PatientNotFoundException;
import com.gisma.pams.model.Payment;
import com.gisma.pams.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PatientService patientService;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository,
                         PatientService patientService) {
        this.paymentRepository = paymentRepository;
        this.patientService = patientService;
    }

    public Payment processPayment(Payment payment) {
        validatePayment(payment);
        payment.setTransactionId(UUID.randomUUID().toString());
        return paymentRepository.save(payment);
    }

    public Payment getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PatientNotFoundException("Payment not found with ID: " + paymentId));
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment updatePayment(Long paymentId, Payment paymentDetails) {
        Payment existingPayment = getPaymentById(paymentId);

        if (paymentDetails.getPaymentStatus() != null) {
            existingPayment.setPaymentStatus(paymentDetails.getPaymentStatus());
        }
        if (paymentDetails.getDescription() != null) {
            existingPayment.setDescription(paymentDetails.getDescription());
        }

        return paymentRepository.save(existingPayment);
    }

    public void deletePayment(Long paymentId) {
        Payment payment = getPaymentById(paymentId);
        paymentRepository.delete(payment);
    }

    public List<Payment> getPatientPayments(Long patientId) {
        patientService.getPatientById(patientId);
        return paymentRepository.findByPatientPatientId(patientId);
    }

    public List<Payment> getPaymentsByStatus(Payment.PaymentStatus status) {
        return paymentRepository.findByPaymentStatus(status);
    }

    public List<Payment> getPaymentsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return paymentRepository.findPaymentsBetweenDates(startDate, endDate);
    }

    public BigDecimal getTotalRevenue(LocalDateTime startDate, LocalDateTime endDate) {
        BigDecimal total = paymentRepository.getTotalRevenueInPeriod(startDate, endDate);
        return total != null ? total : BigDecimal.ZERO;
    }

    public BigDecimal getTotalRevenue() {
        return getTotalRevenue(null, null);
    }

    public Payment completePayment(Long paymentId) {
        Payment payment = getPaymentById(paymentId);
        if (payment.getPaymentStatus() == Payment.PaymentStatus.COMPLETED) {
            throw new IllegalArgumentException("Payment is already completed");
        }
        payment.setPaymentStatus(Payment.PaymentStatus.COMPLETED);
        return paymentRepository.save(payment);
    }

    public Payment refundPayment(Long paymentId) {
        Payment payment = getPaymentById(paymentId);
        payment.setPaymentStatus(Payment.PaymentStatus.REFUNDED);
        return paymentRepository.save(payment);
    }

    // Method aliases to match controller expectations
    public List<Payment> getPaymentsByPatientId(Long patientId) {
        return getPatientPayments(patientId);
    }

    public List<Payment> getPaymentsByAppointmentId(Long appointmentId) {
        return paymentRepository.findByAppointmentAppointmentId(appointmentId);
    }

    public BigDecimal getTotalRevenueByPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        return getTotalRevenue(startDate, endDate);
    }

    private void validatePayment(Payment payment) {
        if (payment.getPatient() == null || payment.getPatient().getPatientId() == null) {
            throw new IllegalArgumentException("Patient is required");
        }
        if (payment.getAmount() == null || payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (payment.getPaymentMethod() == null || payment.getPaymentMethod().isBlank()) {
            throw new IllegalArgumentException("Payment method is required");
        }
    }

    public long getTotalPaymentsCount() {
        return paymentRepository.count();
    }

    public long getCompletedPaymentsCount() {
        return paymentRepository.findByPaymentStatus(Payment.PaymentStatus.COMPLETED).size();
    }
}
