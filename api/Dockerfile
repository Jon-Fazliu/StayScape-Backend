FROM eclipse-temurin:21-jdk
WORKDIR /application

RUN apt update && apt install -y curl && rm -rf /var/lib/apt/lists/*

COPY build/libs/api.jar ./api.jar
COPY docker_run.sh ./

CMD ["./docker_run.sh", "api.jar"]
EXPOSE 8080
