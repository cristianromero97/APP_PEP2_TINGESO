FROM openjdk:17
ARG JAR_FILE=target/client-service-backend.jar
COPY ${JAR_FILE} client-service-backend.jar
ENTRYPOINT ["java","-jar","/client-service-backend.jar"]