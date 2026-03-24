package com.gisma.pams.dto;

public class NotificationDTO {
    private Long notificationId;
    private Long patientId;
    private Long doctorId;
    private String notificationType;
    private String title;
    private String message;
    private Boolean isRead;
    private String status;
    private String createdAt;
    private String updatedAt;

    public NotificationDTO() {
    }

    public NotificationDTO(Long notificationId, Long patientId, Long doctorId,
                           String notificationType, String title, String message,
                           Boolean isRead, String status, String createdAt, String updatedAt) {
        this.notificationId = notificationId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.notificationType = notificationType;
        this.title = title;
        this.message = message;
        this.isRead = isRead;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getNotificationId() { return notificationId; }
    public void setNotificationId(Long notificationId) { this.notificationId = notificationId; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
    public String getNotificationType() { return notificationType; }
    public void setNotificationType(String notificationType) { this.notificationType = notificationType; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Boolean getIsRead() { return isRead; }
    public void setIsRead(Boolean isRead) { this.isRead = isRead; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
