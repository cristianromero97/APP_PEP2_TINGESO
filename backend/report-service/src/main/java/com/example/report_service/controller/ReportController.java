package com.example.report_service.controller;

import com.example.report_service.entity.ReportEntity;
import com.example.report_service.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // Create a new report controller
    @PostMapping("/register")
    public ResponseEntity<ReportEntity> generateReport(@RequestBody ReportEntity reportEntity) {
        ReportEntity generated = reportService.generateReport(reportEntity);
        return ResponseEntity.ok(generated);
    }

    // Get all reports controller
    @GetMapping("/")
    public ResponseEntity<List<ReportEntity>> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    // Get report by id controller
    @GetMapping("/{id}")
    public ResponseEntity<ReportEntity> getReportById(@PathVariable Long id) {
        Optional<ReportEntity> report = reportService.getReportById(id);
        return report.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Update a report controler
    @PutMapping("/{id}")
    public ResponseEntity<ReportEntity> updateReport(@PathVariable Long id, @RequestBody ReportEntity reportEntity) {
        try {
            return ResponseEntity.ok(reportService.updateReport(id, reportEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a report controller
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        try {
            reportService.deleteReport(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
