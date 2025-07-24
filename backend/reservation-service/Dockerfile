FROM openjdk:17
ARG JAR_FILE=target/reservation-service-backend.jar
COPY ${JAR_FILE} reservation-service-backend.jar
ENTRYPOINT ["java","-jar","/reservation-service-backend.jar"]