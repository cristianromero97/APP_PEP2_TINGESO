FROM openjdk:17
ARG JAR_FILE=target/weeklyrack-service-backend.jar
COPY ${JAR_FILE} weeklyrack-service-backend.jar
ENTRYPOINT ["java","-jar","/weeklyrack-service-backend.jar"]