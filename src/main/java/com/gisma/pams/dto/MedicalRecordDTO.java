package com.gisma.pams.dto;

public class MedicalRecordDTO {
    private Long recordId;
    private Long patientId;
    private Long appointmentId;
    private Long doctorId;
    private String recordType;
    private String diagnosis;
    private String treatmentPlan;
    private String clinicalNotes;
    private String allergies;
    private String medication;
    private String status;
    private String createdAt;
    private String updatedAt;

    public MedicalRecordDTO() {
    }

    public MedicalRecordDTO(Long recordId, Long patientId, Long appointmentId, Long doctorId,
                            String recordType, String diagnosis, String treatmentPlan,
                            String clinicalNotes, String allergies, String medication,
                            String status, String createdAt, String updatedAt) {
        this.recordId = recordId;
        this.patientId = patientId;
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
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

    public Long getRecordId() { return recordId; }
    public void setRecordId(Long recordId) { this.recordId = recordId; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public Long getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Long appointmentId) { this.appointmentId = appointmentId; }
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
    public String getRecordType() { return recordType; }
    public void setRecordType(String recordType) { this.recordType = recordType; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public String getTreatmentPlan() { return treatmentPlan; }
    public void setTreatmentPlan(String treatmentPlan) { this.treatmentPlan = treatmentPlan; }
    public String getClinicalNotes() { return clinicalNotes; }
    public void setClinicalNotes(String clinicalNotes) { this.clinicalNotes = clinicalNotes; }
    public String getAllergies() { return allergies; }
    public void setAllergies(String allergies) { this.allergies = allergies; }
    public String getMedication() { return medication; }
    public void setMedication(String medication) { this.medication = medication; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
