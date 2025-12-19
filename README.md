# Health Check Demo

Spring Boot + MySQL + Redis 헬스체크 시스템

---

## 실행 방법

### 프로덕션 실행

```bash
# 1. Docker로 MySQL, Redis 실행
docker-compose up -d

# 2. 확인
curl http://localhost:8080/health
```

### 테스트 실행

```bash
# Cucumber BDD 테스트 (Testcontainers 자동 실행)
./gradlew test
```

테스트 실행 시:
- MySQL, Redis 컨테이너 자동 생성 (Testcontainers)
- 모든 테스트가 같은 컨테이너 재사용 (빠른 실행)
- 시나리오마다 DB/Redis 자동 초기화

---

## API 목록

| Method | Path | Description | Response |
|--------|------|-------------|----------|
| GET | `/health` | 시스템 헬스체크 | `OK` (200) |

## 기술 스택

- Java 17
- Spring Boot 3.4.0
- MySQL 8.0, Redis 7
- Cucumber, Testcontainers
