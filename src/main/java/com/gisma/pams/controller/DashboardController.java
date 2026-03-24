package com.gisma.pams.controller;

import com.gisma.pams.dto.DashboardStatsDTO;
import com.gisma.pams.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DashboardController {

    private final DashboardService dashboardService;

    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        try {
            DashboardStatsDTO stats = dashboardService.getDashboardStats();
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Dashboard statistics retrieved successfully");
            response.put("data", stats);
            response.put("status", "success");
            response.put("statusCode", 200);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving dashboard stats: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/stats/month")
    public ResponseEntity<Map<String, Object>> getDashboardStatsByMonth(@RequestParam int year, @RequestParam int month) {
        try {
            if (month < 1 || month > 12) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Invalid month. Please provide month between 1 and 12");
                response.put("status", "error");
                response.put("statusCode", 400);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            DashboardStatsDTO stats = dashboardService.getDashboardStatsByMonth(year, month);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Dashboard statistics for " + year + "-" + String.format("%02d", month) + " retrieved successfully");
            response.put("data", stats);
            response.put("status", "success");
            response.put("statusCode", 200);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving dashboard stats: " + e.getMessage());
            response.put("status", "error");
            response.put("statusCode", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
