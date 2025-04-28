FROM openjdk:17 AS builder

COPY . .
RUN ./mvnw clean install -DskipTests

FROM openjdk:17

COPY --from=builder target/*.jar b2c_payment_service.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/b2c_payment_service.jar"]
