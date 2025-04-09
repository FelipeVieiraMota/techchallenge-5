# JAVA 17.......

# O código-fonte da aplicação é copiado para o diretório /app e o Maven é executado
# para gerar o pacote (excluindo os testes para agilizar o processo de build).
# Fase de Build
FROM maven:3.9.9-eclipse-temurin-17 AS build
# Install curl
RUN apt-get update && apt-get install -y curl

# Verify curl installation
RUN curl --version

COPY ../src /app/src
COPY ../pom.xml /app
WORKDIR /app
RUN mvn clean package -DskipTests

# O arquivo JAR gerado na fase de build é copiado para o diretório /app e o container
# é configurado para expor a porta 9000.
FROM eclipse-temurin:17-jre
RUN groupadd --system spring && useradd --system --gid spring --create-home spring
USER spring:spring
COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 9000
ENTRYPOINT ["java","-jar","/app/app.jar"]