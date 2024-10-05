FROM openjdk:17-jdk-alpine
COPY ./target/*.jar parcial1-app.jar
ENTRYPOINT ["java","-jar","parcial1-app.jar"]