package com.gisma.pams.service;

import com.gisma.pams.exception.DiseaseNotFoundException;
import com.gisma.pams.model.Disease;
import com.gisma.pams.repository.DiseaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DiseaseService {

    private final DiseaseRepository diseaseRepository;

    @Autowired
    public DiseaseService(DiseaseRepository diseaseRepository) {
        this.diseaseRepository = diseaseRepository;
    }

    public Disease createDisease(Disease disease) {
        validateDiseaseData(disease);
        checkDuplicateDisease(disease.getDiseaseName());
        return diseaseRepository.save(disease);
    }

    public Disease getDiseaseById(Long diseaseId) {
        return diseaseRepository.findById(diseaseId)
                .orElseThrow(() -> new DiseaseNotFoundException("Disease not found with ID: " + diseaseId));
    }

    public List<Disease> getAllDiseases() {
        return diseaseRepository.findAll();
    }

    public Disease updateDisease(Long diseaseId, Disease diseaseDetails) {
        Disease existingDisease = getDiseaseById(diseaseId);

        if (diseaseDetails.getDiseaseName() != null && !diseaseDetails.getDiseaseName().isBlank()) {
            if (!existingDisease.getDiseaseName().equalsIgnoreCase(diseaseDetails.getDiseaseName())) {
                checkDuplicateDisease(diseaseDetails.getDiseaseName());
            }
            existingDisease.setDiseaseName(diseaseDetails.getDiseaseName());
        }
        if (diseaseDetails.getDescription() != null) {
            existingDisease.setDescription(diseaseDetails.getDescription());
        }
        if (diseaseDetails.getSymptoms() != null) {
            existingDisease.setSymptoms(diseaseDetails.getSymptoms());
        }
        if (diseaseDetails.getTreatment() != null) {
            existingDisease.setTreatment(diseaseDetails.getTreatment());
        }

        return diseaseRepository.save(existingDisease);
    }

    public void deleteDisease(Long diseaseId) {
        Disease disease = getDiseaseById(diseaseId);
        diseaseRepository.delete(disease);
    }

    public Disease getDiseaseByName(String diseaseName) {
        return diseaseRepository.findByDiseaseName(diseaseName)
                .orElseThrow(() -> new DiseaseNotFoundException("Disease not found: " + diseaseName));
    }

    public List<Disease> searchDiseasesByName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Search term cannot be empty");
        }
        return diseaseRepository.searchByName(name);
    }

    private void validateDiseaseData(Disease disease) {
        if (disease.getDiseaseName() == null || disease.getDiseaseName().isBlank()) {
            throw new IllegalArgumentException("Disease name is required");
        }
    }

    private void checkDuplicateDisease(String diseaseName) {
        if (diseaseRepository.findByDiseaseName(diseaseName).isPresent()) {
            throw new IllegalArgumentException("Disease already exists: " + diseaseName);
        }
    }

    public long getTotalDiseasesCount() {
        return diseaseRepository.count();
    }

    public boolean existsByName(String diseaseName) {
        return diseaseRepository.findByDiseaseName(diseaseName).isPresent();
    }
}
