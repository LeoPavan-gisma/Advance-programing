package com.gisma.pams.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "prescriptions")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prescriptionId;

    @NotBlank(message = "Medicine name is required")
    @Column(nullable = false)
    private String medicineName;

    @Positive(message = "Dosage must be positive")
    @Column(nullable = false)
    private Double dosage;

    @NotBlank(message = "Unit is required")
    @Column(nullable = false)
    private String unit;

    @NotBlank(message = "Frequency is required")
    @Column(nullable = false)
    private String frequency;

    @Column(nullable = false)
    private Integer durationDays;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(columnDefinition = "TEXT")
    private String sideEffects;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // No-args constructor
    public Prescription() {
    }

    // All-args constructor
    public Prescription(Long prescriptionId, String medicineName, Double dosage,
                        String unit, String frequency, Integer durationDays,
                        String notes, String sideEffects, Appointment appointment,
                        Doctor doctor, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.prescriptionId = prescriptionId;
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.unit = unit;
        this.frequency = frequency;
        this.durationDays = durationDays;
        this.notes = notes;
        this.sideEffects = sideEffects;
        this.appointment = appointment;
        this.doctor = doctor;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters
    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public Double getDosage() {
        return dosage;
    }

    public String getUnit() {
        return unit;
    }

    public String getFrequency() {
        return frequency;
    }

    public Integer getDurationDays() {
        return durationDays;
    }

    public String getNotes() {
        return notes;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Setters
    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public void setDosage(Double dosage) {
        this.dosage = dosage;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
