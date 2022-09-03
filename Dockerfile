FROM alpine
RUN apk update
RUN apk add busybox-extras
FROM openjdk:8-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY healthy /tmp
ENTRYPOINT ["java","-jar","/app.jar", "--spring.profiles.active=docker"]
