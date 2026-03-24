package com.gisma.pams.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(name = "medical_records")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @NotBlank(message = "Record type is required")
    @Column(nullable = false)
    private String recordType;

    @Column(columnDefinition = "TEXT")
    private String diagnosis;

    @Column(columnDefinition = "TEXT")
    private String treatmentPlan;

    @Column(columnDefinition = "TEXT")
    private String clinicalNotes;

    @Column(columnDefinition = "TEXT")
    private String allergies;

    @Column(columnDefinition = "TEXT")
    private String medication;

    @NotBlank(message = "Record status is required")
    @Column(nullable = false)
    private String status;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // No-args constructor
    public MedicalRecord() {
    }

    // All-args constructor
    public MedicalRecord(Long recordId, Patient patient, Appointment appointment, Doctor doctor,
                         String recordType, String diagnosis, String treatmentPlan,
                         String clinicalNotes, String allergies, String medication,
                         String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.recordId = recordId;
        this.patient = patient;
        this.appointment = appointment;
        this.doctor = doctor;
        this.recordType = recordType;
        this.diagnosis = diagnosis;
        this.treatmentPlan = treatmentPlan;
        this.clinicalNotes = clinicalNotes;
        this.allergies = allergies;
        this.medication = medication;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters
    public Long getRecordId() { return recordId; }
    public Patient getPatient() { return patient; }
    public Appointment getAppointment() { return appointment; }
    public Doctor getDoctor() { return doctor; }
    public String getRecordType() { return recordType; }
    public String getDiagnosis() { return diagnosis; }
    public String getTreatmentPlan() { return treatmentPlan; }
    public String getClinicalNotes() { return clinicalNotes; }
    public String getAllergies() { return allergies; }
    public String getMedication() { return medication; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setters
    public void setRecordId(Long recordId) { this.recordId = recordId; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public void setAppointment(Appointment appointment) { this.appointment = appointment; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
    public void setRecordType(String recordType) { this.recordType = recordType; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public void setTreatmentPlan(String treatmentPlan) { this.treatmentPlan = treatmentPlan; }
    public void setClinicalNotes(String clinicalNotes) { this.clinicalNotes = clinicalNotes; }
    public void setAllergies(String allergies) { this.allergies = allergies; }
    public void setMedication(String medication) { this.medication = medication; }
    public void setStatus(String status) { this.status = status; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

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
