# Usar una imagen base de OpenJDK 21
FROM eclipse-temurin:21-jdk

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el código fuente y las carpetas necesarias
# No copiamos frames/ porque ya la eliminamos
COPY src ./src
COPY lib ./lib
COPY manifest.mf .

# Crear el directorio de datos para la persistencia
RUN mkdir -p /app/data

# Compilar todos los archivos .java dentro de src de forma recursiva
# Excluimos cualquier cosa que pueda haber quedado de frames o interfaces gráficas
RUN find src -name "*.java" > sources.txt && \
    javac -d build -cp "lib/*:src" @sources.txt

# Crear el JAR ejecutable
# El manifest.mf ya tiene Main-Class: Main
RUN jar cmf manifest.mf BiblioRepo.jar -C build .

# Definir el volumen para la persistencia
VOLUME /app/data

# Comando para ejecutar la aplicación de consola
# -it se usa al correr el contenedor para interactuar con el Scanner
ENTRYPOINT ["java", "-Djava.awt.headless=true", "-jar", "BiblioRepo.jar"]
