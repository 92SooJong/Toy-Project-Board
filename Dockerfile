FROM openjdk:8-jdk-alpine

WORKDIR /workspace/app

ARG JAR_FILE

COPY ${JAR_FILE} app.jar
COPY application-db.yml /application-db.yml

ENTRYPOINT ["/bin/sh","-c","java -Dspring.config.location=classpath:/application-${IDLE_PROFILE}.yml,/application-db.yml -Dspring.profiles.active=${IDLE_PROFILE} -jar ./app.jar"]