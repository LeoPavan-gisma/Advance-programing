package com.gisma.pams.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(name = "patient_tests")
public class PatientTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @NotBlank(message = "Test name is required")
    @Column(nullable = false)
    private String testName;

    @NotBlank(message = "Test type is required")
    @Column(nullable = false)
    private String testType;

    @Column(columnDefinition = "TEXT")
    private String result;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @Column(columnDefinition = "TEXT")
    private String normalRange;

    @NotBlank(message = "Test status is required")
    @Column(nullable = false)
    private String testStatus;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // No-args constructor
    public PatientTest() {
    }

    // All-args constructor
    public PatientTest(Long testId, Patient patient, Doctor doctor, String testName,
                       String testType, String result, String remarks, 
                       String normalRange, String testStatus, LocalDateTime createdAt, 
                       LocalDateTime updatedAt) {
        this.testId = testId;
        this.patient = patient;
        this.doctor = doctor;
        this.testName = testName;
        this.testType = testType;
        this.result = result;
        this.remarks = remarks;
        this.normalRange = normalRange;
        this.testStatus = testStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters
    public Long getTestId() { return testId; }
    public Patient getPatient() { return patient; }
    public Doctor getDoctor() { return doctor; }
    public String getTestName() { return testName; }
    public String getTestType() { return testType; }
    public String getResult() { return result; }
    public String getRemarks() { return remarks; }
    public String getNormalRange() { return normalRange; }
    public String getTestStatus() { return testStatus; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setters
    public void setTestId(Long testId) { this.testId = testId; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
    public void setTestName(String testName) { this.testName = testName; }
    public void setTestType(String testType) { this.testType = testType; }
    public void setResult(String result) { this.result = result; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public void setNormalRange(String normalRange) { this.normalRange = normalRange; }
    public void setTestStatus(String testStatus) { this.testStatus = testStatus; }
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
