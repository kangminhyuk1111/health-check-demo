package com.healthcheckdemo;

import com.healthcheckdemo.config.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class HealthCheckDemoApplicationTests {

  @Test
  void contextLoads() {
  }

}
