# Build Stage (빌드 단계)
# Gradle 8.14와 JDK 17이 포함된 이미지를 빌드 단계의 베이스로 사용
# AS build: 이 단계를 'build'라는 이름으로 지정하여 나중에 참조 가능
FROM gradle:8.14-jdk17 AS build

# 컨테이너 내부의 작업 디렉토리를 /app으로 설정
# 이후 모든 명령은 이 디렉토리에서 실행됨
WORKDIR /app

# ---- 의존성 캐싱 최적화 ----
# Gradle 설정 파일들만 먼저 복사
# 이렇게 하면 소스 코드가 변경되어도 의존성 레이어는 캐시를 재사용할 수 있음
COPY build.gradle settings.gradle ./
COPY gradle ./gradle

# Gradle 의존성을 미리 다운로드
# --no-daemon: Gradle 데몬을 사용하지 않음 (Docker 환경에서 불필요)
# || true: 실패해도 계속 진행 (의존성 캐싱이 목적이므로)
RUN gradle dependencies --no-daemon || true

# ---- 소스 코드 복사 및 빌드 ----
# 애플리케이션 소스 코드를 컨테이너로 복사
COPY src ./src

# 애플리케이션 빌드
# --no-daemon: Gradle 데몬 비활성화 (메모리 절약)
# -x test: 테스트는 건너뛰고 빌드만 수행 (CI/CD에서 별도로 테스트 실행 권장)
RUN gradle build --no-daemon -x test

# Runtime Stage (실행 단계)
# Eclipse Temurin JRE 17 (OpenJDK 기반)을 베이스 이미지로 사용
FROM eclipse-temurin:17-jre-alpine

# 컨테이너 내부의 작업 디렉토리를 /app으로 설정
WORKDIR /app

# ---- 보안 설정: non-root 사용자 생성 ----
# spring이라는 그룹과 사용자를 생성
# -S: 시스템 계정으로 생성 (일반 로그인 불가)
# 보안 모범 사례: 컨테이너를 root가 아닌 일반 사용자로 실행
RUN addgroup -S spring && adduser -S -G spring spring

# ---- 빌드된 JAR 파일 복사 ----
# --from=build: 'build' 단계에서 파일을 복사
# /app/build/libs/*.jar: 빌드 단계에서 생성된 JAR 파일
# app.jar: 실행 단계에서 사용할 파일명으로 변경
COPY --from=build /app/build/libs/*.jar app.jar

# /app 디렉토리의 소유권을 spring 사용자와 그룹으로 변경
# 이렇게 해야 spring 사용자가 파일에 접근 가능
RUN chown -R spring:spring /app

# 이후 모든 명령을 spring 사용자로 실행
# 보안 강화: 애플리케이션이 root 권한 없이 실행됨
USER spring

# ---- 포트 설정 ----
# 컨테이너가 8080 포트를 사용한다고 문서화
# 실제로 포트를 여는 것은 아니며, 문서화 목적
# 실행 시 -p 옵션으로 포트 매핑 필요: docker run -p 8080:8080
EXPOSE 8080

# ---- 애플리케이션 실행 ----
# 컨테이너가 시작될 때 실행할 명령
# ENTRYPOINT: docker run 시 변경할 수 없는 고정 명령 (CMD와 달리)
# java -jar app.jar: Spring Boot 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
