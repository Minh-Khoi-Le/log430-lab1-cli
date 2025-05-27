# Phase 1: Build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Phase 2: Run
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/caisse-pos-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
