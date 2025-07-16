FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app
COPY . .

RUN mvn install:install-file \
    -Dfile=libs/domain.jar \
    -DgroupId=net.dimjasevic.karlo.fer.evidentor \
    -DartifactId=domain \
    -Dversion=0.0.4-SNAPSHOT \
    -Dpackaging=jar

RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jre-alpine

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

ENTRYPOINT ["java", "--enable-preview", "-jar", "app.jar"]
