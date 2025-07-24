FROM openjdk:17
ARG JAR_FILE=target/discount-day-service-backend.jar
COPY ${JAR_FILE} discount-day-service-backend.jar
ENTRYPOINT ["java","-jar","/discount-day-service-backend.jar"]