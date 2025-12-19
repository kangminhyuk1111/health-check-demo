package com.healthcheckdemo.cucumber.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

/**
 * Health Check Step Definitions
 * feature 파일에 작성한 시나리오를 실제 코드로 연결
 */
public class HealthCheckSteps {

  @Autowired
  private TestRestTemplate restTemplate;

  // Step들 사이에서 공유할 응답 객체
  private ResponseEntity<String> response;

  /**
   * @When: feature 파일의 When 키워드랑 매핑
   * {string}: 따옴표 안의 값을 파라미터로 받음
   * 예) When 클라이언트가 GET "/health" API를 요청하면
   *     -> path에 "/health" 전달됨
   */
  @When("클라이언트가 GET {string} API를 요청하면")
  public void 클라이언트가GET_HealthAPI를_요청하면(String path) {
    response = restTemplate.getForEntity(path, String.class);
  }

  /**
   * @Then: 예상 결과 검증
   * {int}: 숫자 파라미터 받기
   * 예) Then 응답 상태 코드는 200 이어야 한다
   *     -> statusCode에 200 전달됨
   */
  @Then("응답 상태 코드는 {int} 이어야 한다")
  public void 응답_상태코드는_이어야한다(int statusCode) {
    assertThat(response.getStatusCode().value()).isEqualTo(statusCode);
  }

  /**
   * @And: 추가 검증 (Then이랑 같은데 가독성을 위해)
   */
  @And("응답 본문은 {string} 이어야 한다")
  public void 응답_본문은_이어야한다(String text) {
    assertThat(response.getBody()).isEqualTo(text);
  }
}
