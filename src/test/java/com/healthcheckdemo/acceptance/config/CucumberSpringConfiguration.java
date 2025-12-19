package com.healthcheckdemo.acceptance.config;

import com.healthcheckdemo.config.TestcontainersConfiguration;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * Cucumber + Spring Boot 통합 설정
 * Testcontainers로 MySQL, Redis 띄워서 테스트
 */
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
public class CucumberSpringConfiguration {
}
