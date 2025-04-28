FROM openjdk:17-oracle

WORKDIR /app

COPY . .

RUN ./mvnw clean install -DskipTests

EXPOSE 8080

COPY target/*.jar /app/b2c-app.jar

ENTRYPOINT ["java", "-jar", "/app/b2c-app.jar"]
