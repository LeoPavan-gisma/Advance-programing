package com.gisma.pams.service;

import com.gisma.pams.exception.PatientNotFoundException;
import com.gisma.pams.model.PatientTest;
import com.gisma.pams.repository.PatientTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PatientTestService {

    private final PatientTestRepository patientTestRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;

    @Autowired
    public PatientTestService(PatientTestRepository patientTestRepository,
                              PatientService patientService,
                              DoctorService doctorService) {
        this.patientTestRepository = patientTestRepository;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    public PatientTest createTest(PatientTest test) {
        validateTest(test);
        return patientTestRepository.save(test);
    }

    public PatientTest getTestById(Long testId) {
        return patientTestRepository.findById(testId)
                .orElseThrow(() -> new PatientNotFoundException("Test not found with ID: " + testId));
    }

    public List<PatientTest> getAllTests() {
        return patientTestRepository.findAll();
    }

    public PatientTest updateTest(Long testId, PatientTest testDetails) {
        PatientTest existingTest = getTestById(testId);

        if (testDetails.getTestName() != null && !testDetails.getTestName().isBlank()) {
            existingTest.setTestName(testDetails.getTestName());
        }
        if (testDetails.getTestType() != null && !testDetails.getTestType().isBlank()) {
            existingTest.setTestType(testDetails.getTestType());
        }
        if (testDetails.getResult() != null) {
            existingTest.setResult(testDetails.getResult());
        }
        if (testDetails.getRemarks() != null) {
            existingTest.setRemarks(testDetails.getRemarks());
        }
        if (testDetails.getNormalRange() != null) {
            existingTest.setNormalRange(testDetails.getNormalRange());
        }
        if (testDetails.getTestStatus() != null) {
            existingTest.setTestStatus(testDetails.getTestStatus());
        }

        return patientTestRepository.save(existingTest);
    }

    public void deleteTest(Long testId) {
        PatientTest test = getTestById(testId);
        patientTestRepository.delete(test);
    }

    public List<PatientTest> getPatientTests(Long patientId) {
        patientService.getPatientById(patientId);
        return patientTestRepository.findByPatientPatientId(patientId);
    }

    public List<PatientTest> getPatientTestHistory(Long patientId) {
        patientService.getPatientById(patientId);
        return patientTestRepository.getPatientTestHistory(patientId);
    }

    public List<PatientTest> getDoctorTests(Long doctorId) {
        doctorService.getDoctorById(doctorId);
        return patientTestRepository.findByDoctorDoctorId(doctorId);
    }

    public List<PatientTest> getTestsByType(String testType) {
        return patientTestRepository.findByTestType(testType);
    }

    public List<PatientTest> getTestsByStatus(String testStatus) {
        return patientTestRepository.findByTestStatus(testStatus);
    }

    // Method aliases to match controller expectations
    public PatientTest createPatientTest(PatientTest test) {
        return createTest(test);
    }

    public PatientTest getPatientTestById(Long testId) {
        return getTestById(testId);
    }

    public List<PatientTest> getAllPatientTests() {
        return getAllTests();
    }

    public PatientTest updatePatientTest(Long testId, PatientTest testDetails) {
        return updateTest(testId, testDetails);
    }

    public void deletePatientTest(Long testId) {
        deleteTest(testId);
    }

    private void validateTest(PatientTest test) {
        if (test.getPatient() == null || test.getPatient().getPatientId() == null) {
            throw new IllegalArgumentException("Patient is required");
        }
        if (test.getDoctor() == null || test.getDoctor().getDoctorId() == null) {
            throw new IllegalArgumentException("Doctor is required");
        }
        if (test.getTestName() == null || test.getTestName().isBlank()) {
            throw new IllegalArgumentException("Test name is required");
        }
        if (test.getTestType() == null || test.getTestType().isBlank()) {
            throw new IllegalArgumentException("Test type is required");
        }
    }

    public long getTotalTestsCount() {
        return patientTestRepository.count();
    }
}
