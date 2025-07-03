FROM openjdk:17-jdk-slim

LABEL maintainer="nekocurit@gmail.com"

COPY build/install/LQ2TG /app

RUN mkdir -p /app/storage

WORKDIR /app/storage

VOLUME ["/app/storage"]

CMD ["/app/bin/LQ2TG"]