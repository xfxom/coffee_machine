FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
VOLUME /tmp

ARG JAR_FILE=/app/target/machine-0.0.1.jar

COPY --from=build ${JAR_FILE} app.jar

ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app.jar"]
