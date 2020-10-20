FROM openjdk:11-jre-slim
COPY ./build/libs/*.jar pick-saturn.jar
ENTRYPOINT ["java", "-Xmx300m", "-jar", "-Duser.timezone=Asia/Seoul", "/pick-saturn.jar"]