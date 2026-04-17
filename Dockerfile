FROM eclipse-temurin:17-jdk

# Establecer el directorio de trabajo para el código
WORKDIR /app

# Copiar el código fuente y las librerías
COPY src/ /app/src/
COPY lib/ /app/lib/

# Crear directorios necesarios
# Se crea /data en la raíz para evitar confusiones de rutas relativas
# Se crea /app/out para los binarios
RUN mkdir -p /data /app/out && chmod 777 /data

# Compilar todos los archivos Java
RUN javac -cp "/app/lib/*" -d /app/out $(find /app/src -name "*.java")

# Declarar el volumen de datos (donde persistirá la info)
VOLUME /data

# Ejecutar la aplicación
# Definimos el classpath incluyendo /app/out y las librerías
CMD ["java", "-cp", "/app/out:/app/lib/*", "Main"]
