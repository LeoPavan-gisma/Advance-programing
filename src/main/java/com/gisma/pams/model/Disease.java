package com.gisma.pams.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "diseases")
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diseaseId;

    @NotBlank(message = "Disease name is required")
    @Column(nullable = false, unique = true)
    private String diseaseName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String symptoms;

    @Column(columnDefinition = "TEXT")
    private String treatment;

    @Column(name = "created_at", updatable = false)
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    // No-args constructor
    public Disease() {
    }

    // All-args constructor
    public Disease(Long diseaseId, String diseaseName, String description,
                   String symptoms, String treatment, String createdAt, String updatedAt) {
        this.diseaseId = diseaseId;
        this.diseaseName = diseaseName;
        this.description = description;
        this.symptoms = symptoms;
        this.treatment = treatment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters
    public Long getDiseaseId() { return diseaseId; }
    public String getDiseaseName() { return diseaseName; }
    public String getDescription() { return description; }
    public String getSymptoms() { return symptoms; }
    public String getTreatment() { return treatment; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }

    // Setters
    public void setDiseaseId(Long diseaseId) { this.diseaseId = diseaseId; }
    public void setDiseaseName(String diseaseName) { this.diseaseName = diseaseName; }
    public void setDescription(String description) { this.description = description; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }
    public void setTreatment(String treatment) { this.treatment = treatment; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    @PrePersist
    protected void onCreate() {
        this.createdAt = java.time.LocalDateTime.now().toString();
        this.updatedAt = java.time.LocalDateTime.now().toString();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = java.time.LocalDateTime.now().toString();
    }
}
