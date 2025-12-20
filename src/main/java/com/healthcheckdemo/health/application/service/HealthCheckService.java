package com.healthcheckdemo.health.application.service;

import com.healthcheckdemo.health.application.port.in.HealthCheckUseCase;
import com.healthcheckdemo.health.application.port.out.DatabaseConnectionPort;
import com.healthcheckdemo.health.application.port.out.KafkaConnectionPort;
import com.healthcheckdemo.health.application.port.out.RedisConnectionPort;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HealthCheckService implements HealthCheckUseCase {

    private final DatabaseConnectionPort databaseConnectionPort;
    private final RedisConnectionPort redisConnectionPort;
    private final KafkaConnectionPort kafkaConnectionPort;

    public HealthCheckService(DatabaseConnectionPort databaseConnectionPort, RedisConnectionPort redisConnectionPort, final KafkaConnectionPort kafkaConnectionPort) {
        this.databaseConnectionPort = databaseConnectionPort;
        this.redisConnectionPort = redisConnectionPort;
        this.kafkaConnectionPort = kafkaConnectionPort;
    }

    @Override
    public String checkHealth() {
        return "OK";
    }
}
