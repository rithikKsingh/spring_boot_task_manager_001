FROM openjdk:27-ea-oraclelinux9
ADD target/taskManager-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]