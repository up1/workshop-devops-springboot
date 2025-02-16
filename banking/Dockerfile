#----------------------------------
# Stage 1
#----------------------------------

# Import docker image with maven installed
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder 

# Set working directory
WORKDIR /app

# Copy source code from local to container
COPY . /app

# Build application and skip test cases
RUN mvn clean install -DskipTests=true

#--------------------------------------
# Stage 2
#--------------------------------------

# Import small size java image
FROM eclipse-temurin:21.0.6_7-jre-alpine-3.21 AS deployer

# Copy build from stage 1 (builder)
COPY --from=builder /app/target/*.jar /app/target/banking.jar

# Expose application port 
EXPOSE 8080
    

# Specify JVM options (if needed, adjust as necessary)
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Start the application with JVM options
ENTRYPOINT ["java", "-jar", "/app/target/banking.jar", "-Djava.security.egd=file:/dev/./urandom"]