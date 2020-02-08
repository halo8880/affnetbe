#!/bin/bash
#git clone https://github.com/halo8880/affnetfe
#git clone https://github.com/halo8880/affnetbe
git -C affnetfe checkout gpt
git -C affnetfe pull
git -C affnetbe checkout gpt
git -C affnetbe pull

cd affnetfe && npm install && npm run build
cd ../affnetbe && ./mvnw clean package
cd ../
docker-compose -f compose.yml down
docker-compose -f compose.yml build
docker-compose -f compose.yml up -d
