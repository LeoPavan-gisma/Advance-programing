package com.gisma.pams.dto;

public class DiseaseDTO {
    private Long diseaseId;
    private String diseaseName;
    private String description;
    private String symptoms;
    private String treatment;
    private String createdAt;
    private String updatedAt;

    public DiseaseDTO() {
    }

    public DiseaseDTO(Long diseaseId, String diseaseName, String description,
                      String symptoms, String treatment, String createdAt, String updatedAt) {
        this.diseaseId = diseaseId;
        this.diseaseName = diseaseName;
        this.description = description;
        this.symptoms = symptoms;
        this.treatment = treatment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getDiseaseId() { return diseaseId; }
    public void setDiseaseId(Long diseaseId) { this.diseaseId = diseaseId; }
    public String getDiseaseName() { return diseaseName; }
    public void setDiseaseName(String diseaseName) { this.diseaseName = diseaseName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getSymptoms() { return symptoms; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }
    public String getTreatment() { return treatment; }
    public void setTreatment(String treatment) { this.treatment = treatment; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
