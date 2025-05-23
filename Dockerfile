#FROM openjdk:8-jdk-alpine
#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring
#ARG DEPENDENCY=target/dependency
#COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY ${DEPENDENCY}/META-INF /app/META-INF
#COPY ${DEPENDENCY}/BOOT-INF/classes /app
#ENTRYPOINT ["java","-cp","app:app/lib/*","hello.Application"]


# Use official OpenJDK runtime as base image
#FROM oracle/jdk:
FROM openjdk:21-jdk

# Set working directory
WORKDIR /app

# add user and dependency
#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring
#ARG DEPENDENCY=target/dependency
#COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY ${DEPENDENCY}/META-INF /app/META-INF
#COPY ${DEPENDENCY}/BOOT-INF/classes /app

# Copy the JAR file into the container
COPY target/saasrecarlo.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

#Step 1: Build the Spring Boot application
#mvn clean package
#Step 2: Build Docker image
#docker build -t saasrecarlo .
#Step 3: Run the container:
#docker run -p 8080:8080 saasrecarlo  or docker run -p 8080:8080 saasrecarlo
#Step 4: visit http://localhost:8080/  or curl http://localhost:8080
# Additional Useful Commands
# View running containers:
#	docker ps
# View logs:
#	docker logs saasrecarlo
# Stop the container:
#	docker stop saasrecarlo
# Remove the container:
#	docker rm saasrecarlo
#
#
#Alternative
#Step 1: create docker-compose.yml
#version: '3.9'
#services:
#  app:
#    build: .
#    ports:
#      - "8080:8080"
#    environment:
#      - SPRING_PROFILES_ACTIVE=docker
#Step 2: docker-compose up --build

