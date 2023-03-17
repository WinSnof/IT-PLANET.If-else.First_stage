FROM openjdk:19
COPY target/contest-0.0.1-SNAPSHOT.jar contest-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/contest-0.0.1-SNAPSHOT.jar"]