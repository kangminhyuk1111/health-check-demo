package com.healthcheckdemo.health.adapter.in.web;

import com.healthcheckdemo.health.application.port.in.HealthCheckUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    private final HealthCheckUseCase healthCheckUseCase;

    public HealthController(HealthCheckUseCase healthCheckUseCase) {
        this.healthCheckUseCase = healthCheckUseCase;
    }

    @GetMapping("/health")
    public String health() {
        return healthCheckUseCase.checkHealth();
    }
}
