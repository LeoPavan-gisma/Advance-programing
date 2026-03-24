package com.gisma.pams.repository;

import com.gisma.pams.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findByPatientPatientId(Long patientId);
    List<MedicalRecord> findByDoctorDoctorId(Long doctorId);
    List<MedicalRecord> findByAppointmentAppointmentId(Long appointmentId);
    List<MedicalRecord> findByRecordType(String recordType);
    List<MedicalRecord> findByStatus(String status);
    
    @Query("SELECT mr FROM MedicalRecord mr WHERE mr.patient.patientId = :patientId ORDER BY mr.createdAt DESC")
    List<MedicalRecord> getPatientMedicalHistory(@Param("patientId") Long patientId);
}
