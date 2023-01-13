FROM openjdk:8-jdk-alpine
#ARG JAR_FILE=target/*.jar
COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
https://github.com/boulbeba115/devOpsProj/blob/master/Dockerfile
