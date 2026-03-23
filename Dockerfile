FROM eclipse-temurin:25-jdk

EXPOSE 8080

COPY target/authorizationServiceRest-0.0.1-SNAPSHOT.jar authorization_service.jar

CMD ["java", "-jar", "authorization_service.jar"]