# ---- build stage ----
FROM maven:3.9.6-amazoncorretto-21 AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# ---- runtime stage ----
FROM amazoncorretto:21
WORKDIR /app
COPY --from=build /app/target/DocLib-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
