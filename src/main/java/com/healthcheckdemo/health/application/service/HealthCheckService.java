package com.healthcheckdemo.health.application.service;

import com.healthcheckdemo.health.application.port.in.HealthCheckUseCase;
import com.healthcheckdemo.health.application.port.out.DatabaseConnectionPort;
import com.healthcheckdemo.health.application.port.out.RedisConnectionPort;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HealthCheckService implements HealthCheckUseCase {

    private final DatabaseConnectionPort databaseConnectionPort;
    private final RedisConnectionPort redisConnectionPort;

    public HealthCheckService(DatabaseConnectionPort databaseConnectionPort, RedisConnectionPort redisConnectionPort) {
        this.databaseConnectionPort = databaseConnectionPort;
        this.redisConnectionPort = redisConnectionPort;
    }

    // @PostConstruct - 객체 생성이 끝난 이후 실행 (Spring Bean 생성 완료 후)
    @PostConstruct
    public void verifyConnections() {
        List<String> failedConnections = new ArrayList<>();

        if (!databaseConnectionPort.isConnected()) {
            failedConnections.add(databaseConnectionPort.getConnectionName());
        }
        if (!redisConnectionPort.isConnected()) {
            failedConnections.add(redisConnectionPort.getConnectionName());
        }

        if (!failedConnections.isEmpty()) {
            throw new IllegalStateException(
                    "연결 실패: " + String.join(", ", failedConnections));
        }
    }

    @Override
    public String checkHealth() {
        return "OK";
    }
}
