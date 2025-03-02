# Use a base image with Java 23
FROM openjdk:23-jdk-slim as Development

# Set the working directory inside the container
WORKDIR /app

# Expose the port that the Spring Boot application runs on
EXPOSE 8080
EXPOSE 8000:8000

ENV JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000"

# Copy the built JAR file into the container
COPY target/ImageService-0.0.1-SNAPSHOT.jar /app/ImageService-0.0.1-SNAPSHOT.jar

# Run the Spring Boot application
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000", "-jar", "/app/ImageService-0.0.1-SNAPSHOT.jar"]
