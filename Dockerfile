# Use an official OpenJDK runtime as a parent image for building
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build

# Set the working directory in the container
WORKDIR /app

# Install Maven
RUN apk add --no-cache maven

# Copy the Maven build file to leverage Docker cache
COPY pom.xml .

# Download dependencies only (cache optimization)
RUN mvn dependency:go-offline

# Copy the project source code to the container
COPY src ./src

# Build the application inside the container
RUN mvn clean package -DskipTests

# Use a minimal JDK runtime for the final image
FROM eclipse-temurin:17-jre-alpine

# Set the working directory in the container
WORKDIR /app

# Copy only the built JAR from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
