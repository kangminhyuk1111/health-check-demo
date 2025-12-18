package com.healthcheckdemo.cucumber.steps;

import static org.assertj.core.api.Assertions.assertThat;

import com.healthcheckdemo.cucumber.CucumberSpringConfiguration;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

public class HealthCheckSteps {

  @Autowired
  private TestRestTemplate restTemplate;

  private ResponseEntity<String> response;

  @When("the client requests GET {string}")
  public void the_client_requests_get(String path) {
    // 실제 HTTP GET 요청 발생
    response = restTemplate.getForEntity(path, String.class);
  }

  @Then("the response status should be {int}")
  public void the_response_status_should_be(Integer statusCode) {
    assertThat(response.getStatusCode().value()).isEqualTo(statusCode);
  }

  @Then("the response body should be {string}")
  public void the_response_body_should_be(String expectedBody) {
    assertThat(response.getBody()).isEqualTo(expectedBody);
  }
}
