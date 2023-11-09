FROM openjdk:19
WORKDIR /app
COPY /app-faz-feira.jar app-faz-feira.jar
EXPOSE 8084
CMD ["java", "-jar", "app-faz-feira.jar"]
