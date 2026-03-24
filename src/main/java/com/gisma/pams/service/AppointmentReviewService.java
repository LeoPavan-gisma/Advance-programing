package com.gisma.pams.service;

import com.gisma.pams.exception.PatientNotFoundException;
import com.gisma.pams.model.AppointmentReview;
import com.gisma.pams.repository.AppointmentReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AppointmentReviewService {

    private final AppointmentReviewRepository appointmentReviewRepository;
    private final PatientService patientService;
    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentReviewService(AppointmentReviewRepository appointmentReviewRepository,
                                    PatientService patientService,
                                    AppointmentService appointmentService) {
        this.appointmentReviewRepository = appointmentReviewRepository;
        this.patientService = patientService;
        this.appointmentService = appointmentService;
    }

    public AppointmentReview createReview(AppointmentReview review) {
        validateReview(review);
        return appointmentReviewRepository.save(review);
    }

    public AppointmentReview getReviewById(Long reviewId) {
        return appointmentReviewRepository.findById(reviewId)
                .orElseThrow(() -> new PatientNotFoundException("Review not found with ID: " + reviewId));
    }

    public List<AppointmentReview> getAllReviews() {
        return appointmentReviewRepository.findAll();
    }

    public AppointmentReview updateReview(Long reviewId, AppointmentReview reviewDetails) {
        AppointmentReview existingReview = getReviewById(reviewId);

        if (reviewDetails.getRating() != null && reviewDetails.getRating() >= 1 && reviewDetails.getRating() <= 5) {
            existingReview.setRating(reviewDetails.getRating());
        }
        if (reviewDetails.getComment() != null) {
            existingReview.setComment(reviewDetails.getComment());
        }
        if (reviewDetails.getRecommendations() != null) {
            existingReview.setRecommendations(reviewDetails.getRecommendations());
        }

        return appointmentReviewRepository.save(existingReview);
    }

    public void deleteReview(Long reviewId) {
        AppointmentReview review = getReviewById(reviewId);
        appointmentReviewRepository.delete(review);
    }

    public AppointmentReview getAppointmentReview(Long appointmentId) {
        return appointmentReviewRepository.findByAppointmentAppointmentId(appointmentId)
                .orElseThrow(() -> new PatientNotFoundException("Review not found for appointment: " + appointmentId));
    }

    public List<AppointmentReview> getPatientReviews(Long patientId) {
        patientService.getPatientById(patientId);
        return appointmentReviewRepository.findByPatientPatientId(patientId);
    }

    public List<AppointmentReview> getReviewsByRating(Integer rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        return appointmentReviewRepository.findByRating(rating);
    }

    public Double getDoctorAverageRating(Long doctorId) {
        return appointmentReviewRepository.getAverageDoctorRating(doctorId);
    }

    public Long getDoctorReviewCount(Long doctorId) {
        return appointmentReviewRepository.getDoctorReviewCount(doctorId);
    }

    // Method aliases to match controller expectations
    public AppointmentReview createAppointmentReview(AppointmentReview review) {
        return createReview(review);
    }

    public AppointmentReview getAppointmentReviewById(Long reviewId) {
        return getReviewById(reviewId);
    }

    public List<AppointmentReview> getAllAppointmentReviews() {
        return getAllReviews();
    }

    public AppointmentReview getAppointmentReviewByAppointmentId(Long appointmentId) {
        return getAppointmentReview(appointmentId);
    }

    public AppointmentReview updateAppointmentReview(Long reviewId, AppointmentReview reviewDetails) {
        return updateReview(reviewId, reviewDetails);
    }

    public void deleteAppointmentReview(Long reviewId) {
        deleteReview(reviewId);
    }

    private void validateReview(AppointmentReview review) {
        if (review.getAppointment() == null || review.getAppointment().getAppointmentId() == null) {
            throw new IllegalArgumentException("Appointment is required");
        }
        if (review.getPatient() == null || review.getPatient().getPatientId() == null) {
            throw new IllegalArgumentException("Patient is required");
        }
        if (review.getRating() == null || review.getRating() < 1 || review.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
    }

    public long getTotalReviewsCount() {
        return appointmentReviewRepository.count();
    }
}
