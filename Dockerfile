FROM openjdk:17-jdk-slim
COPY /target/app-faz-feira.jar app-faz-feira.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","app-faz-feira.jar"]