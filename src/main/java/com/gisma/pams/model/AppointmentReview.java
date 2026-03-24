package com.gisma.pams.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointment_reviews")
public class AppointmentReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointment_id", nullable = false, unique = true)
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot exceed 5")
    @Column(nullable = false)
    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(columnDefinition = "TEXT")
    private String recommendations;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // No-args constructor
    public AppointmentReview() {
    }

    // All-args constructor
    public AppointmentReview(Long reviewId, Appointment appointment, Patient patient,
                             Integer rating, String comment, String recommendations,
                             LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.reviewId = reviewId;
        this.appointment = appointment;
        this.patient = patient;
        this.rating = rating;
        this.comment = comment;
        this.recommendations = recommendations;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters
    public Long getReviewId() { return reviewId; }
    public Appointment getAppointment() { return appointment; }
    public Patient getPatient() { return patient; }
    public Integer getRating() { return rating; }
    public String getComment() { return comment; }
    public String getRecommendations() { return recommendations; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setters
    public void setReviewId(Long reviewId) { this.reviewId = reviewId; }
    public void setAppointment(Appointment appointment) { this.appointment = appointment; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public void setRating(Integer rating) { this.rating = rating; }
    public void setComment(String comment) { this.comment = comment; }
    public void setRecommendations(String recommendations) { this.recommendations = recommendations; }
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
