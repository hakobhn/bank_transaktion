################################################
FROM adoptopenjdk/openjdk11:alpine-jre

RUN apk update && apk add --no-cache bash
RUN apk add --no-cache msttcorefonts-installer fontconfig
RUN apk add --update ttf-dejavu

RUN mkdir workspace
WORKDIR workspace

RUN chmod +x gradlew

RUN ./gradlew build -x test

COPY --from=0 /dev-workspace/build/libs/bank_transaction_toolkit-0.0.1-SNAPSHOT.jar app.jar
#ADD ./build/libs/bank_transaction_toolkit-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8090

ENTRYPOINT ["java","-Dspring.profiles.active=development","-jar","app.jar"]
