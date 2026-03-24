package com.gisma.pams.repository;

import com.gisma.pams.model.AppointmentReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentReviewRepository extends JpaRepository<AppointmentReview, Long> {
    Optional<AppointmentReview> findByAppointmentAppointmentId(Long appointmentId);
    List<AppointmentReview> findByPatientPatientId(Long patientId);
    List<AppointmentReview> findByRating(Integer rating);
    
    @Query("SELECT AVG(ar.rating) FROM AppointmentReview ar WHERE ar.appointment.doctor.doctorId = :doctorId")
    Double getAverageDoctorRating(@Param("doctorId") Long doctorId);
    
    @Query("SELECT COUNT(ar) FROM AppointmentReview ar WHERE ar.appointment.doctor.doctorId = :doctorId")
    Long getDoctorReviewCount(@Param("doctorId") Long doctorId);
}
