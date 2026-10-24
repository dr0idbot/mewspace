# Render deploys this with the Docker runtime (dockerContext = repo root).

# Stage 1 — build every module and package the mewspace-ui boot jar.
# Maven 3.9 + Temurin JDK 25 (noble base, matched with the runtime image).
FROM maven:3.9-eclipse-temurin-25-noble AS build
WORKDIR /build

# Poms first so dependency layers survive source churn between deploys.
COPY pom.xml ./
COPY mewspace-backend/pom.xml ./mewspace-backend/pom.xml
COPY mewspace-views/pom.xml ./mewspace-views/pom.xml
COPY mewspace-ui/pom.xml ./mewspace-ui/pom.xml
RUN mvn -q -B -DskipTests dependency:go-offline || true

# Sources, then a reactor build that also runs the Vaadin production
# frontend build (build-frontend), so the runtime never needs Node.
COPY mewspace-backend/src ./mewspace-backend/src
COPY mewspace-views/src ./mewspace-views/src
COPY mewspace-ui/src ./mewspace-ui/src
RUN mvn -q -B -DskipTests package

# Stage 2 — JRE-only runtime, ~310MB, no build tooling.
FROM eclipse-temurin:25-jre-noble
WORKDIR /app
COPY --from=build --chown=ubuntu:ubuntu /build/mewspace-ui/target/mewspace-ui-1.0-SNAPSHOT.jar /app/app.jar
USER ubuntu
EXPOSE 8080
# PORT is injected by Render; the jar binds ${PORT:8080}.
# Sized for the free tier (0.1 CPU / 512MB): single CPU, low-overhead GC.
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=80.0", "-XX:ActiveProcessorCount=1", "-XX:+UseSerialGC", "-XX:+ExitOnOutOfMemoryError", "-jar", "/app/app.jar"]
