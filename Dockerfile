#FROM openjdk:8-jdk-alpine
FROM openjdk:11
VOLUME /tmp
ADD target/*.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["java","-jar","/app.jar"]




