# Feature: 테스트할 기능
Feature: Health Check

  # Scenario: 실제 테스트 케이스 (Given-When-Then 패턴)
  Scenario: 시스템이 건강한지 확인한다
    When 클라이언트가 GET "/health" API를 요청하면
    Then 응답 상태 코드는 200 이어야 한다
    And 응답 본문은 "OK" 이어야 한다

  Scenario: 잘못된 경로로 요청한다.
    When 클라이언트가 GET "/wrong" API를 요청하면
    Then 응답 상태 코드는 404 이어야 한다

# Gherkin 기본 키워드
# - Feature: 기능 이름
# - Scenario: 테스트 케이스
# - Given: 초기 상태
# - When: 실행할 동작
# - Then: 예상 결과
# - And/But: 추가 조건
#
# 파라미터 타입
# {string} - 문자열
# {int} - 정수
# {float} - 실수
# {word} - 단어
