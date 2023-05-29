FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /app
COPY target/azure-file-share-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/app.jar"]
