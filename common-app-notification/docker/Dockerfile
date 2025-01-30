FROM alpine/java:21-jdk
ARG JAR_FILE=../build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=dev -Dserver.port=7530 -jar /app.jar"]
#