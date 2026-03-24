package com.gisma.pams.repository;

import com.gisma.pams.model.PatientTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientTestRepository extends JpaRepository<PatientTest, Long> {
    List<PatientTest> findByPatientPatientId(Long patientId);
    List<PatientTest> findByDoctorDoctorId(Long doctorId);
    List<PatientTest> findByTestName(String testName);
    List<PatientTest> findByTestType(String testType);
    List<PatientTest> findByTestStatus(String testStatus);
    
    @Query("SELECT pt FROM PatientTest pt WHERE pt.patient.patientId = :patientId ORDER BY pt.createdAt DESC")
    List<PatientTest> getPatientTestHistory(@Param("patientId") Long patientId);
}
