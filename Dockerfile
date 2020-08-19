FROM openjdk:11-jre-slim
COPY ./pick-server-saturn/build/libs/*.jar pick-saturn.jar
ENTRYPOINT ["java", "-jar", "/pick-saturn.jar"]
