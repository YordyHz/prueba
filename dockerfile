FROM openjdk:8-jdk-alpine
COPY tarjet/prueba-0.0.1-SNAPSHOT.jar java-app.jar
ENTRYPOINT ["java", "-jar", "java-app.jar"]