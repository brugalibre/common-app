FROM alpine/java:21-jdk
ARG JAR_FILE=../build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=prod -Dserver.port=7530 -jar /app.jar"]
# Befor the first use create shared network
# docker network create alert-send-shared