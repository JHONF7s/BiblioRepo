FROM eclipse-temurin:17-jdk

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el código fuente y las librerías al contenedor
COPY src/ /app/src/
COPY lib/ /app/lib/

# Compilar todos los archivos Java incluyendo la librería en el classpath (usando : como separador en Linux)
RUN mkdir -p /app/out && javac -cp "/app/lib/*" -d /app/out $(find /app/src -name "*.java")

# Ejecutar la clase Main incluyendo los binarios y la librería en el classpath
CMD ["java", "-cp", "/app/out:/app/lib/*", "Main"]
