FROM eclipse-temurin:17-jdk-jammy as builder

WORKDIR /app

COPY build.gradle.kts settings.gradle.kts /app/
COPY gradle /app/gradle
COPY src /app/src
COPY gradlew.bat /app/
COPY gradle.properties /app/
COPY gradlew /app/

RUN ./gradlew shadowJar --no-daemon

FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY --from=builder /app/build/libs/*-all.jar /app/app.jar

# COPY .env /app/.env
COPY ./application.yaml /app/application.yaml

CMD ["echo", "$JWT_SECRET"]
CMD ["echo", "hello world"]
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]