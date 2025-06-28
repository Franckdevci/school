FROM openjdk:8-jdk-alpine
EXPOSE 8082
ADD target/school-1.1.0.jar school-1.1.0.jar
ENTRYPOINT ["java","-jar","/school-1.1.0.jar"]