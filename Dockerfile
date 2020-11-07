FROM openjdk:15-jdk-alpine
WORKDIR /app
EXPOSE 4000
COPY build/libs/maven-parser-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "maven-parser-0.0.1-SNAPSHOT.jar"]