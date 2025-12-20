# 1단계: 빌드 스테이지
FROM eclipse-temurin:17-jdk-jammy AS builder

WORKDIR /app

COPY . .

# gradlew 실행 권한 추가
RUN chmod +x ./gradlew

# 테스트 제외하고 빌드
RUN ./gradlew build -x test --no-daemon

# 2단계: 최종 이미지
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]