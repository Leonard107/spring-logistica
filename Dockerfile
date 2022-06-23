FROM openjdk:11-jre-slim 

#Define o diretorio de trabalho
WORKDIR /app

#Criando argumento para receber oe essa variavel vai receber como argumento
ARG JAR_FILE

#Recebe da origem para o destino da imagem do container
COPY target/${JAR_FILE} /app/api.jar

#Informa qual porta o container vai escutar quando ele tiver rodando
EXPOSE 8080

#Comando padrão que vai ser rodado quando o container iniciar
CMD ["java", "-jar", "api.jar"]