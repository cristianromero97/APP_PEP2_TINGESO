FROM openjdk:17
ARG JAR_FILE=target/eureka-service-backend.jar
COPY ${JAR_FILE} eureka-service-backend.jar
ENTRYPOINT ["java","-jar","/eureka-service-backend.jar"]