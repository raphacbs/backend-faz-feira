#
# Build stage
#
FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY --from=build /target/app-faz-feira.jar app-faz-feira.jar
# ENV PORT=8080
EXPOSE 8081
ENTRYPOINT ["java","-jar","app-faz-feira.jar"]