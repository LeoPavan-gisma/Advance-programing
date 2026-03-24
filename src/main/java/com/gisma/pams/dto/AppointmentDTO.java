package com.gisma.pams.dto;

import java.math.BigDecimal;

public class AppointmentDTO {
    private Long appointmentId;
    private PatientDTO patient;
    private DoctorDTO doctor;
    private DiseaseDTO disease;
    private String appointmentDateTime;
    private String reason;
    private String status;
    private String notes;
    private String createdAt;
    private String updatedAt;

    public AppointmentDTO() {
    }

    public AppointmentDTO(Long appointmentId, PatientDTO patient, DoctorDTO doctor,
                          DiseaseDTO disease, String appointmentDateTime, String reason,
                          String status, String notes, String createdAt, String updatedAt) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.disease = disease;
        this.appointmentDateTime = appointmentDateTime;
        this.reason = reason;
        this.status = status;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Long appointmentId) { this.appointmentId = appointmentId; }
    public PatientDTO getPatient() { return patient; }
    public void setPatient(PatientDTO patient) { this.patient = patient; }
    public DoctorDTO getDoctor() { return doctor; }
    public void setDoctor(DoctorDTO doctor) { this.doctor = doctor; }
    public DiseaseDTO getDisease() { return disease; }
    public void setDisease(DiseaseDTO disease) { this.disease = disease; }
    public String getAppointmentDateTime() { return appointmentDateTime; }
    public void setAppointmentDateTime(String appointmentDateTime) { this.appointmentDateTime = appointmentDateTime; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
