#FROM openjdk:11-jre-slim
FROM openjdk:11-jre-alpine
COPY ./build/libs/*.jar pick-saturn.jar
ENTRYPOINT ["java", "-Xmx200m", "-jar", "-Duser.timezone=Asia/Seoul", "/pick-saturn.jar"]