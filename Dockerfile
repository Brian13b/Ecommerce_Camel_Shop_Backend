# Etapa 1: Build con Maven y Java 21
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copiar solo el pom.xml primero para aprovechar la caché de Docker
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el código fuente y compilar
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Runtime ligero
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Crear un usuario no root por seguridad (opcional pero recomendado)
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copiar el JAR desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Variables de entorno para optimizar la JVM en 512MB
# -Xmx300M: Límite de memoria heap
# -Xss512K: Reduce el tamaño de los hilos para ahorrar RAM
# -XX:+UseSerialGC: Recolector de basura de bajo consumo
ENV JAVA_OPTS="-Xmx300M -Xms150M -Xss512K -XX:MaxMetaspaceSize=100M -XX:+UseSerialGC"

EXPOSE 8080

# Ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]