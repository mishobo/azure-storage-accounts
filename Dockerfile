FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]
