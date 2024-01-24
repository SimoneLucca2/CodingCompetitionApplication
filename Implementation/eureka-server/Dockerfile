# Start with a base image containing Java runtime (adoptopenjdk is an open-source JDK)
FROM adoptopenjdk:11-jre-hotspot as builder

# Add Maintainer Info
LABEL maintainer="your-email@example.com"

# The application's jar file
ARG JAR_FILE=target/*.jar

# Add the application's jar to the container
COPY ${JAR_FILE} app.jar

# Run the jar file 
ENTRYPOINT ["java","-jar","/app.jar"]
