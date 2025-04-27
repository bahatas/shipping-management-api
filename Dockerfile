# Derleme aşaması

FROM maven:3.8.6-amazoncorretto-21 AS build


  # Çalışma dizinini ayarla
WORKDIR /app

  # Önce sadece pom.xml'i kopyala ve bağımlılıkları indir
  # Bu, Docker katmanlarının önbelleğe alınmasını optimize eder
COPY pom.xml .
RUN mvn dependency:go-offline -B

  # Kaynak kodunu kopyala ve uygulamayı derle
COPY src ./src
RUN mvn package -DskipTests

  # Çalışma aşaması
FROM amazoncorretto:21-alpine


WORKDIR /app

  # Derleme aşamasından JAR dosyasını kopyala
COPY --from=build /app/target/*.jar app.jar

# JVM ayarları için ortam değişkenleri
ENV JAVA_OPTS="-Xms512m -Xmx1024m"

# Uygulama portunu belirt
EXPOSE 8081

# Uygulamayı başlat
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=stag -jar shipping-management-api.jar"]







