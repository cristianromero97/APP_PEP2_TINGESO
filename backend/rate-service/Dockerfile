FROM openjdk:17
ARG JAR_FILE=target/rate-service-backend.jar
COPY ${JAR_FILE} rate-service-backend.jar
ENTRYPOINT ["java","-jar","/rate-service-backend.jar"]