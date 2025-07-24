FROM openjdk:17
ARG JAR_FILE=target/kart-service-backend.jar
COPY ${JAR_FILE} kart-service-backend.jar
ENTRYPOINT ["java","-jar","/kart-service-backend.jar"]