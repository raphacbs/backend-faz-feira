FROM openjdk:19
WORKDIR /app
COPY /target/app-faz-feira.jar app-faz-feira.jar
# ENV PORT=8080
EXPOSE 8081
ENTRYPOINT ["java","-jar","app-faz-feira.jar"]