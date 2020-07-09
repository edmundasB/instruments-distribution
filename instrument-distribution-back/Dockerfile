FROM openjdk:8-jdk-alpine
FROM maven:alpine

WORKDIR /app
ADD pom.xml /app

RUN mvn clean install -DskipTests

EXPOSE 8081
ADD ./target/homework-0.0.1-SNAPSHOT.jar homework.jar
ENTRYPOINT ["java", "-jar", "homework.jar"]
