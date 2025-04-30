# Derleme aşaması
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn package -DskipTests

# Çalıştırma aşaması
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.jar shipping-management-api.jar

ENV JAVA_OPTS="-Xms512m -Xmx1024m"

EXPOSE 8081

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=stag -jar shipping-management-api.jar"]
