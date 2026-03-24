package com.gisma.pams.dto;

public class AppointmentReviewDTO {
    private Long reviewId;
    private Long appointmentId;
    private Long patientId;
    private Integer rating;
    private String comment;
    private String recommendations;
    private String createdAt;
    private String updatedAt;

    public AppointmentReviewDTO() {
    }

    public AppointmentReviewDTO(Long reviewId, Long appointmentId, Long patientId,
                                Integer rating, String comment, String recommendations,
                                String createdAt, String updatedAt) {
        this.reviewId = reviewId;
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.rating = rating;
        this.comment = comment;
        this.recommendations = recommendations;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getReviewId() { return reviewId; }
    public void setReviewId(Long reviewId) { this.reviewId = reviewId; }
    public Long getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Long appointmentId) { this.appointmentId = appointmentId; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public String getRecommendations() { return recommendations; }
    public void setRecommendations(String recommendations) { this.recommendations = recommendations; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
