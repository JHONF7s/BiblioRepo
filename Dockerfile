# Usar una imagen base de OpenJDK
FROM openjdk:17-jdk-slim

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo build.xml y la carpeta src
# Esto es para permitir la compilación con Ant si es necesario, o directamente con javac
COPY build.xml .
COPY src ./src
COPY lib ./lib

# Crear el directorio de datos para la persistencia
RUN mkdir -p /app/data

# Compilar la aplicación. NetBeans usa un 'nbproject' y otras estructuras
# que Ant suele manejar. Si el build.xml es el estándar de NetBeans, debería compilar bien.
# Si hay problemas, se podría simplificar a un 'javac' directo.
# Primero, instalar ant si no está en la imagen base (openjdk:17-jdk-slim no lo trae por defecto)
RUN apt-get update && apt-get install -y ant && rm -rf /var/lib/apt/lists/*

# Ejecutar el target de compilación por defecto de Ant, que típicamente es 'jar' o 'compile'
# Asumo que el build.xml tiene un target para construir el JAR ejecutable o compilar
# En el caso de BiblioRepo, parece generar el JAR en 'dist'
RUN ant -f build.xml clean jar

# Una vez compilado, el JAR estará en dist/. Necesitamos copiarlo a la raíz del WORKDIR.
# Esto asume que el build.xml genera un JAR ejecutable con el MANIFEST.MF configurado.
# Si el nombre del JAR no es BiblioRepo.jar, ajustarlo.
RUN cp dist/BiblioRepo.jar .

# Comando para ejecutar la aplicación
# La clase principal con el método main() es ConsoleApp
CMD ["java", "-jar", "BiblioRepo.jar"]
