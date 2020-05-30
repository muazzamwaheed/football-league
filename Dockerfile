FROM openjdk:8-jdk-alpine
VOLUME /temp
ARG JAR_FILE
COPY ${JAR_FILE} football.jar

RUN mkdir -p /test
WORKDIR /test

COPY /target/football.jar /test/football.jar

USER root

ENTRYPOINT ["java", "-jar", "/test/football.jar"]