package com.gisma.pams.repository;

import com.gisma.pams.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByAppointmentAppointmentId(Long appointmentId);
    List<Prescription> findByDoctorDoctorId(Long doctorId);
    
    @Query("SELECT p FROM Prescription p WHERE p.appointment.patient.patientId = :patientId")
    List<Prescription> findByPatientId(@Param("patientId") Long patientId);
    
    List<Prescription> findByMedicineName(String medicineName);
}
