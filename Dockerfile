FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /app
COPY target/azure-file-share-0.0.1-SNAPSHOT.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]
