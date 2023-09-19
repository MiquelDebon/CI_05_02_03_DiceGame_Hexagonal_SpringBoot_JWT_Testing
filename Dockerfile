FROM eclipse-temurin:17-jdk-focal
ARG JAR_FILE=target/*.jar
COPY ./target/S05T02N01DebonVillagrasaMiquel-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]