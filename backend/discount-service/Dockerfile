FROM openjdk:17
ARG JAR_FILE=target/discount-service-backend.jar
COPY ${JAR_FILE} discount-service-backend.jar
ENTRYPOINT ["java","-jar","/discount-service-backend.jar"]