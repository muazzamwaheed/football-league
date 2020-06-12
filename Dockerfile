FROM openjdk:8-jdk-alpine

ADD target/football.jar football.jar
EXPOSE 9091
ENTRYPOINT ["java", "-jar", "/football.jar"]