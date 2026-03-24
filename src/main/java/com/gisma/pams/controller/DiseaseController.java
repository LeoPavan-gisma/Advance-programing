package com.gisma.pams.controller;

import com.gisma.pams.model.Disease;
import com.gisma.pams.service.DiseaseService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/diseases")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DiseaseController {

    private final DiseaseService diseaseService;

    @Autowired
    public DiseaseController(DiseaseService diseaseService) {
        this.diseaseService = diseaseService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createDisease(@RequestBody @Valid Disease disease) {
        Disease createdDisease = diseaseService.createDisease(disease);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Disease created successfully");
        response.put("data", createdDisease);
        response.put("status", "success");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{diseaseId}")
    public ResponseEntity<Map<String, Object>> getDisease(@PathVariable Long diseaseId) {
        Disease disease = diseaseService.getDiseaseById(diseaseId);
        Map<String, Object> response = new HashMap<>();
        response.put("data", disease);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllDiseases() {
        List<Disease> diseases = diseaseService.getAllDiseases();
        Map<String, Object> response = new HashMap<>();
        response.put("data", diseases);
        response.put("total", diseases.size());
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{diseaseId}")
    public ResponseEntity<Map<String, Object>> updateDisease(
            @PathVariable Long diseaseId,
            @RequestBody @Valid Disease diseaseDetails) {
        Disease updatedDisease = diseaseService.updateDisease(diseaseId, diseaseDetails);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Disease updated successfully");
        response.put("data", updatedDisease);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{diseaseId}")
    public ResponseEntity<Map<String, Object>> deleteDisease(@PathVariable Long diseaseId) {
        diseaseService.deleteDisease(diseaseId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Disease deleted successfully");
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/name/{diseaseName}")
    public ResponseEntity<Map<String, Object>> getDiseaseByName(@PathVariable String diseaseName) {
        Disease disease = diseaseService.getDiseaseByName(diseaseName);
        Map<String, Object> response = new HashMap<>();
        response.put("data", disease);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchDiseases(@RequestParam String name) {
        List<Disease> diseases = diseaseService.searchDiseasesByName(name);
        Map<String, Object> response = new HashMap<>();
        response.put("data", diseases);
        response.put("total", diseases.size());
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/exists/{diseaseName}")
    public ResponseEntity<Map<String, Object>> checkDiseaseExists(@PathVariable String diseaseName) {
        boolean exists = diseaseService.existsByName(diseaseName);
        Map<String, Object> response = new HashMap<>();
        response.put("exists", exists);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Object>> getTotalDiseasesCount() {
        long count = diseaseService.getTotalDiseasesCount();
        Map<String, Object> response = new HashMap<>();
        response.put("total", count);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }
}
