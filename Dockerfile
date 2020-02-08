FROM openjdk:8-jdk
COPY target/timension-backend*.jar /app.jar
ENTRYPOINT ["sh", "-c", "java /app.jar"]
