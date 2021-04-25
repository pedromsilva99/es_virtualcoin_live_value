FROM java:8-jdk-alpine

ADD target/serving-web-content-0.0.1-SNAPSHOT.jar docker-spring-boot.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","docker-spring-boot.jar"]
