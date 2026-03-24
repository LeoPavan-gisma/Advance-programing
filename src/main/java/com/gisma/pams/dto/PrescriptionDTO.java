package com.gisma.pams.dto;

public class PrescriptionDTO {
    private Long prescriptionId;
    private String medicineName;
    private Double dosage;
    private String unit;
    private String frequency;
    private Integer durationDays;
    private String notes;
    private String sideEffects;
    private Long appointmentId;
    private Long doctorId;
    private String createdAt;
    private String updatedAt;

    public PrescriptionDTO() {
    }

    public PrescriptionDTO(Long prescriptionId, String medicineName, Double dosage,
                           String unit, String frequency, Integer durationDays,
                           String notes, String sideEffects, Long appointmentId,
                           Long doctorId, String createdAt, String updatedAt) {
        this.prescriptionId = prescriptionId;
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.unit = unit;
        this.frequency = frequency;
        this.durationDays = durationDays;
        this.notes = notes;
        this.sideEffects = sideEffects;
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getPrescriptionId() { return prescriptionId; }
    public void setPrescriptionId(Long prescriptionId) { this.prescriptionId = prescriptionId; }
    public String getMedicineName() { return medicineName; }
    public void setMedicineName(String medicineName) { this.medicineName = medicineName; }
    public Double getDosage() { return dosage; }
    public void setDosage(Double dosage) { this.dosage = dosage; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }
    public Integer getDurationDays() { return durationDays; }
    public void setDurationDays(Integer durationDays) { this.durationDays = durationDays; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public String getSideEffects() { return sideEffects; }
    public void setSideEffects(String sideEffects) { this.sideEffects = sideEffects; }
    public Long getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Long appointmentId) { this.appointmentId = appointmentId; }
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
