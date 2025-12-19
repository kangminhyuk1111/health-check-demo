FROM gradle:8.0-jdk17 AS builder

WORKDIR /app

COPY . .

# 테스트 제외하고 빌드
RUN gradle build -x test --no-daemon

# 최종 이미지
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
