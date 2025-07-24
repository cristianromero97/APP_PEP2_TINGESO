FROM openjdk:17
ARG JAR_FILE=target/config-service-backend.jar
COPY ${JAR_FILE} config-service-backend.jar
ENTRYPOINT ["java","-jar","/config-service-backend.jar"]