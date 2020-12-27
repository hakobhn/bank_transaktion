FROM gradle:6.3.0-jdk14 AS build-stage
COPY build.gradle /build/
COPY settings.gradle /build/
COPY gradle /build/gradle/
COPY src /build/src/
WORKDIR /build/
RUN gradle build --info

FROM openjdk:14-jdk-alpine AS prod-stage
WORKDIR /app
COPY --from=build-stage /build/build/libs/*.jar /app/
ENTRYPOINT ["java", "-jar", "bank-service'-0.0.1-SNAPSHOT.jar"]
