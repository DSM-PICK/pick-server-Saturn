FROM openjdk:11-jre-slim

EXPOSE 8889

COPY build/libs/*.jar pick-saturn.jar

ENTRYPOINT ["java", "-jar", "/pick-saturn.jar"]
