# Stage 1: Build the project using Maven
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the packaged jar
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=build /app/target/whatsapp-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]