FROM openjdk:17
ARG JAR_FILE=target/report-service-backend.jar
COPY ${JAR_FILE} report-service-backend.jar
ENTRYPOINT ["java","-jar","/report-service-backend.jar"]