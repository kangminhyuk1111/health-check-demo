package com.healthcheckdemo.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;

/**
 * 모든 테스트에서 공통으로 사용할 Testcontainers 설정
 * JVM 전체에서 컨테이너 하나만 띄워서 재사용
 */
@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

  // JVM 전체에서 단 한 번만 생성되는 컨테이너
  private static final MySQLContainer<?> mysqlContainer;
  private static final GenericContainer<?> redisContainer;

  // static 블록: 클래스 로딩 시 딱 한 번만 실행
  static {
    mysqlContainer = new MySQLContainer<>("mysql:8.0")
        .withDatabaseName("testdb")
        .withUsername("testuser")
        .withPassword("testpass123")
        .withReuse(true)  // 테스트 종료 후에도 컨테이너 유지
        .waitingFor(Wait.forListeningPort());

    redisContainer = new GenericContainer<>("redis:7-alpine")
        .withExposedPorts(6379)
        .withReuse(true)
        .waitingFor(Wait.forListeningPort());

    // 컨테이너 시작 (JVM에서 단 한 번만 실행됨)
    mysqlContainer.start();
    redisContainer.start();
  }

  @Bean
  @ServiceConnection(name = "mysql")
  JdbcDatabaseContainer<?> mysqlContainer() {
    return mysqlContainer;  // 이미 시작된 컨테이너 반환
  }

  @Bean
  @ServiceConnection(name = "redis")
  GenericContainer<?> redisContainer() {
    return redisContainer;  // 이미 시작된 컨테이너 반환
  }
}
