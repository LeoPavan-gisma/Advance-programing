package com.gisma.pams.dto;

public class PatientTestDTO {
    private Long testId;
    private Long patientId;
    private Long doctorId;
    private String testName;
    private String testType;
    private String result;
    private String remarks;
    private String normalRange;
    private String testStatus;
    private String createdAt;
    private String updatedAt;

    public PatientTestDTO() {
    }

    public PatientTestDTO(Long testId, Long patientId, Long doctorId, String testName,
                          String testType, String result, String remarks, String normalRange,
                          String testStatus, String createdAt, String updatedAt) {
        this.testId = testId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.testName = testName;
        this.testType = testType;
        this.result = result;
        this.remarks = remarks;
        this.normalRange = normalRange;
        this.testStatus = testStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getTestId() { return testId; }
    public void setTestId(Long testId) { this.testId = testId; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
    public String getTestName() { return testName; }
    public void setTestName(String testName) { this.testName = testName; }
    public String getTestType() { return testType; }
    public void setTestType(String testType) { this.testType = testType; }
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public String getNormalRange() { return normalRange; }
    public void setNormalRange(String normalRange) { this.normalRange = normalRange; }
    public String getTestStatus() { return testStatus; }
    public void setTestStatus(String testStatus) { this.testStatus = testStatus; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
