# Start from a stable Temurin JRE image with full cgroups v2 support
FROM eclipse-temurin:17-jre-jammy

MAINTAINER joao-tinelli

# Optional: safer memory limits for containers
ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

# Copy the application's jar
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

# Run the app
ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]
