FROM gradle:8.12.1-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle build --no-daemon


FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar auth-server.jar
EXPOSE 8180
ENTRYPOINT ["java", "-jar", "auth-server.jar"]
