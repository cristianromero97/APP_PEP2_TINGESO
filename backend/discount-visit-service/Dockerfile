FROM openjdk:17
ARG JAR_FILE=target/discount-visit-service-backend.jar
COPY ${JAR_FILE} discount-visit-service-backend.jar
ENTRYPOINT ["java","-jar","/discount-visit-service-backend.jar"]