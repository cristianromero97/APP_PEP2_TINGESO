FROM openjdk:17
ARG JAR_FILE=target/payment-service-backend.jar
COPY ${JAR_FILE} payment-service-backend.jar
ENTRYPOINT ["java","-jar","/payment-service-backend.jar"]