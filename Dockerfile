FROM maven:3.9.9-eclipse-temurin-21 AS builder

ARG SERVICE_DIR
WORKDIR /workspace

COPY ${SERVICE_DIR}/pom.xml ${SERVICE_DIR}/pom.xml
COPY ${SERVICE_DIR}/src ${SERVICE_DIR}/src

RUN mvn -f ${SERVICE_DIR}/pom.xml -DskipTests clean package

FROM eclipse-temurin:21-jre

ARG SERVICE_DIR
WORKDIR /app

COPY --from=builder /workspace/${SERVICE_DIR}/target/*-SNAPSHOT.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
