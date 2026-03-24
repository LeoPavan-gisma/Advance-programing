package com.gisma.pams.dto;

import java.math.BigDecimal;

public class DashboardStatsDTO {
    private Long totalPatients;
    private Long totalDoctors;
    private Long totalAppointments;
    private Long scheduledAppointments;
    private Long completedAppointments;
    private Long cancelledAppointments;
    private BigDecimal totalRevenue;
    private Double averageRating;
    private Long totalMedicalRecords;
    private Long totalTests;

    public DashboardStatsDTO() {
    }

    public DashboardStatsDTO(Long totalPatients, Long totalDoctors, Long totalAppointments,
                             Long scheduledAppointments, Long completedAppointments,
                             Long cancelledAppointments, BigDecimal totalRevenue,
                             Double averageRating, Long totalMedicalRecords, Long totalTests) {
        this.totalPatients = totalPatients;
        this.totalDoctors = totalDoctors;
        this.totalAppointments = totalAppointments;
        this.scheduledAppointments = scheduledAppointments;
        this.completedAppointments = completedAppointments;
        this.cancelledAppointments = cancelledAppointments;
        this.totalRevenue = totalRevenue;
        this.averageRating = averageRating;
        this.totalMedicalRecords = totalMedicalRecords;
        this.totalTests = totalTests;
    }

    public Long getTotalPatients() { return totalPatients; }
    public void setTotalPatients(Long totalPatients) { this.totalPatients = totalPatients; }
    public Long getTotalDoctors() { return totalDoctors; }
    public void setTotalDoctors(Long totalDoctors) { this.totalDoctors = totalDoctors; }
    public Long getTotalAppointments() { return totalAppointments; }
    public void setTotalAppointments(Long totalAppointments) { this.totalAppointments = totalAppointments; }
    public Long getScheduledAppointments() { return scheduledAppointments; }
    public void setScheduledAppointments(Long scheduledAppointments) { this.scheduledAppointments = scheduledAppointments; }
    public Long getCompletedAppointments() { return completedAppointments; }
    public void setCompletedAppointments(Long completedAppointments) { this.completedAppointments = completedAppointments; }
    public Long getCancelledAppointments() { return cancelledAppointments; }
    public void setCancelledAppointments(Long cancelledAppointments) { this.cancelledAppointments = cancelledAppointments; }
    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }
    public Double getAverageRating() { return averageRating; }
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }
    public Long getTotalMedicalRecords() { return totalMedicalRecords; }
    public void setTotalMedicalRecords(Long totalMedicalRecords) { this.totalMedicalRecords = totalMedicalRecords; }
    public Long getTotalTests() { return totalTests; }
    public void setTotalTests(Long totalTests) { this.totalTests = totalTests; }
}
