FROM openjdk:8-jdk
COPY target/timension-backend*.jar /app.jar
EXPOSE 8080/tcp
ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=prod -jar /app.jar"]
