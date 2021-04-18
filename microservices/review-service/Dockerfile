FROM openjdk:15.0.2

EXPOSE 8082

ADD ./build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]