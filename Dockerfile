FROM openjdk:8-jdk-alpine

COPY /target/football.jar football.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/football.jar"]