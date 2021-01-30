FROM gradle:6.3.0-jdk14 AS build-stage
COPY build.gradle /build/
COPY settings.gradle /build/
COPY gradle /build/gradle/
COPY src /build/src/
WORKDIR /build/
RUN gradle build -x test


FROM openjdk:14-jdk-alpine AS prod-stage
WORKDIR /app
COPY --from=build-stage /build/build/libs/bank-service-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker","app.jar"]
