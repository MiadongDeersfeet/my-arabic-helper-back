# =========================
# Build stage
# =========================
FROM maven:3.9.8-eclipse-temurin-21 AS builder

# 애플리케이션 소스 복사 및 빌드
WORKDIR /app
COPY pom.xml .
COPY src ./src

# 테스트는 배포 속도를 위해 스킵
RUN mvn clean package -DskipTests

# =========================
# Runtime stage
# =========================
FROM eclipse-temurin:21-jre

# Render는 PORT 환경변수를 주입하므로 셸에서 이를 반영해 실행
WORKDIR /app
COPY --from=builder /app/target/backend-0.0.1-SNAPSHOT.jar app.jar

# 앱 포트 (실제 바인딩은 -Dserver.port 로 처리)
EXPOSE 8080

# Render 환경변수 PORT를 우선 사용하고, 없으면 8080 사용
CMD ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar /app/app.jar"]
