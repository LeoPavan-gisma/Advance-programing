package com.gisma.pams.service;

import com.gisma.pams.exception.PatientNotFoundException;
import com.gisma.pams.model.MedicalRecord;
import com.gisma.pams.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;

    @Autowired
    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository,
                                PatientService patientService,
                                DoctorService doctorService) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    public MedicalRecord createRecord(MedicalRecord record) {
        validateRecord(record);
        return medicalRecordRepository.save(record);
    }

    public MedicalRecord getRecordById(Long recordId) {
        return medicalRecordRepository.findById(recordId)
                .orElseThrow(() -> new PatientNotFoundException("Medical record not found with ID: " + recordId));
    }

    public List<MedicalRecord> getAllRecords() {
        return medicalRecordRepository.findAll();
    }

    public MedicalRecord updateRecord(Long recordId, MedicalRecord recordDetails) {
        MedicalRecord existingRecord = getRecordById(recordId);

        if (recordDetails.getRecordType() != null && !recordDetails.getRecordType().isBlank()) {
            existingRecord.setRecordType(recordDetails.getRecordType());
        }
        if (recordDetails.getDiagnosis() != null) {
            existingRecord.setDiagnosis(recordDetails.getDiagnosis());
        }
        if (recordDetails.getTreatmentPlan() != null) {
            existingRecord.setTreatmentPlan(recordDetails.getTreatmentPlan());
        }
        if (recordDetails.getClinicalNotes() != null) {
            existingRecord.setClinicalNotes(recordDetails.getClinicalNotes());
        }
        if (recordDetails.getAllergies() != null) {
            existingRecord.setAllergies(recordDetails.getAllergies());
        }
        if (recordDetails.getMedication() != null) {
            existingRecord.setMedication(recordDetails.getMedication());
        }
        if (recordDetails.getStatus() != null) {
            existingRecord.setStatus(recordDetails.getStatus());
        }

        return medicalRecordRepository.save(existingRecord);
    }

    public void deleteRecord(Long recordId) {
        MedicalRecord record = getRecordById(recordId);
        medicalRecordRepository.delete(record);
    }

    public List<MedicalRecord> getPatientMedicalHistory(Long patientId) {
        patientService.getPatientById(patientId);
        return medicalRecordRepository.getPatientMedicalHistory(patientId);
    }

    public List<MedicalRecord> getDoctorRecords(Long doctorId) {
        doctorService.getDoctorById(doctorId);
        return medicalRecordRepository.findByDoctorDoctorId(doctorId);
    }

    public List<MedicalRecord> getRecordsByType(String recordType) {
        return medicalRecordRepository.findByRecordType(recordType);
    }

    public List<MedicalRecord> getRecordsByStatus(String status) {
        return medicalRecordRepository.findByStatus(status);
    }

    // Method aliases to match controller expectations
    public MedicalRecord createMedicalRecord(MedicalRecord record) {
        return createRecord(record);
    }

    public MedicalRecord getMedicalRecordById(Long recordId) {
        return getRecordById(recordId);
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        return getAllRecords();
    }

    public MedicalRecord updateMedicalRecord(Long recordId, MedicalRecord recordDetails) {
        return updateRecord(recordId, recordDetails);
    }

    public void deleteMedicalRecord(Long recordId) {
        deleteRecord(recordId);
    }

    private void validateRecord(MedicalRecord record) {
        if (record.getPatient() == null || record.getPatient().getPatientId() == null) {
            throw new IllegalArgumentException("Patient is required");
        }
        if (record.getDoctor() == null || record.getDoctor().getDoctorId() == null) {
            throw new IllegalArgumentException("Doctor is required");
        }
        if (record.getRecordType() == null || record.getRecordType().isBlank()) {
            throw new IllegalArgumentException("Record type is required");
        }
    }

    public long getTotalRecordsCount() {
        return medicalRecordRepository.count();
    }
}
