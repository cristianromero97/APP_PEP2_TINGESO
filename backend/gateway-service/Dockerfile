FROM openjdk:17
ARG JAR_FILE=target/gateway-service-backend.jar
COPY ${JAR_FILE} gateway-service-backend.jar
ENTRYPOINT ["java","-jar","/gateway-service-backend.jar"]