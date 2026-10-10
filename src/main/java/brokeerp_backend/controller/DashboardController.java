package brokeerp_backend.controller;

import brokeerp_backend.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/metricas")
    public ResponseEntity<Map<String, Object>> metricas() {
        return ResponseEntity.ok(dashboardService.obtenerMetricas());
    }

    @GetMapping("/polizas-por-aseguradora")
    public ResponseEntity<Map<String, Object>> polizasPorAseguradora() {
        return ResponseEntity.ok(dashboardService.polizasPorAseguradora());
    }
}