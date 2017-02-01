FROM openjdk:8-jre-alpine

ENV PORT 3000
EXPOSE 3000

COPY target/app.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
