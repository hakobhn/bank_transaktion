################################################
FROM gradle:6.7.1-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --debug --no-daemon

FROM adoptopenjdk/openjdk11:alpine-jre

RUN apk update && apk add --no-cache bash
RUN apk add --no-cache msttcorefonts-installer fontconfig
RUN apk add --update ttf-dejavu

ENV profile=local

EXPOSE 8090

COPY --from=build /home/gradle/src/build/libs/*.jar /spring-boot-application.jar

ENTRYPOINT ["java","-Dspring.profiles.active=${profile}","-jar","spring-boot-application.jar"]
