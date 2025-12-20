package com.healthcheckdemo.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * 모든 테스트에서 공통으로 사용할 Testcontainers 설정
 * JVM 전체에서 컨테이너들을 한 번만 띄워서 재사용
 */
@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

    // JVM 전체에서 단 한 번만 생성되는 컨테이너들
    private static final MySQLContainer<?> mysqlContainer;
    private static final GenericContainer<?> redisContainer;
    private static final KafkaContainer kafkaContainer;

    // static 블록: 클래스 로딩 시 딱 한 번만 실행
    static {
        mysqlContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0"))
                .withDatabaseName("testdb")
                .withUsername("testuser")
                .withPassword("testpass123")
                .withReuse(true)
                .waitingFor(Wait.forListeningPort());

        redisContainer = new GenericContainer<>(DockerImageName.parse("redis:7-alpine"))
                .withExposedPorts(6379)
                .withReuse(true)
                .waitingFor(Wait.forListeningPort());

        kafkaContainer = new KafkaContainer(
                DockerImageName.parse("apache/kafka:latest")
                        .asCompatibleSubstituteFor("apache/kafka")
        )
                .withReuse(true);

        // 컨테이너 시작
        mysqlContainer.start();
        redisContainer.start();
        kafkaContainer.start();
    }

    @Bean
    @ServiceConnection(name = "mysql")
    JdbcDatabaseContainer<?> mysqlContainer() {
        return mysqlContainer;
    }

    @Bean
    @ServiceConnection(name = "redis")
    GenericContainer<?> redisContainer() {
        return redisContainer;
    }

    @Bean
    @ServiceConnection
    KafkaContainer kafkaContainer() {
        return kafkaContainer;
    }
}