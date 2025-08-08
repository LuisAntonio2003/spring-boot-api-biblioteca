# Usa una imagen base de OpenJDK 21, que es ligera y adecuada para contenedores
FROM openjdk:21-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR de tu aplicación al contenedor
# Asegúrate de que el nombre del archivo JAR coincida con el que generaste
COPY target/spring-boot-api-biblioteca-0.0.1-SNAPSHOT.jar /app/app.jar

# Copia el archivo de configuración
COPY src/main/resources/application.yml /app/application.yml

# Configura la zona horaria para la JVM
ENV JAVA_TOOL_OPTIONS="-Duser.timezone=America/Mexico_City"

# Expone el puerto 8080, que es el puerto por defecto de tu aplicación Spring Boot
EXPOSE 8080

# Define el comando que se ejecutará cuando el contenedor se inicie
ENTRYPOINT ["java", "-jar", "app.jar"]
