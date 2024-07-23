#!/bin/bash

echo "> [RUN] Build boot jar"
./gradlew clean bootJar

echo "> [RUN] Build Dockerfile"
docker build -t f1c-server .

echo "> [RUN] Stop Previous f1c-server"
docker stop f1c-server

echo "> [RUN] Remove Previous f1c-server"
docker rm f1c-server

echo "> [RUN] Run Docker"
docker run -p 8080:8080 --name f1c-server -d f1c-server
