FROM java:8
WORKDIR /
ADD demo-0.0.1-SNAPSHOT.jar demo-0.0.1-SNAPSHOT.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","demo-0.0.1-SNAPSHOT.jar"]
