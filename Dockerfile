# 1단계: 빌드 스테이지
FROM openjdk:21-jdk-slim AS spring-build

WORKDIR /spring
COPY ./ .
RUN ls -la
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test

# 2단계: 실행 스테이지
FROM openjdk:21-jdk-slim AS spring-jar

WORKDIR /app

COPY --from=spring-build /spring/build/libs/user_service-0.0.1-SNAPSHOT.jar /app/app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=local", "app.jar"]