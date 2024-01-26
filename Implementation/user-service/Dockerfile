# Start with a base image containing Java runtime
FROM openjdk:17 as build

# Copy the local code to the container
COPY . /usr/src/app

# Set the working directory in the container
WORKDIR /usr/src/app

# Convert line endings in mvnw from CRLF to LF
RUN sed -i 's/\r$//' mvnw

# Make sure the script is executable
RUN chmod +x mvnw

# Package the application
RUN ./mvnw clean package

# Use a new stage to create a smaller final image
FROM openjdk:17

# Copy the jar from the previous stage
COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar

# Expose the port the app runs on
EXPOSE 8081

# Run the jar file
ENTRYPOINT ["java","-jar","/usr/app/app.jar"]
