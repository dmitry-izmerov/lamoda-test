#!/bin/sh

cd ../ && mvn clean package
cp ./target/ROOT.war ./docker/ROOT.war
cd ./docker

docker build -t lamoda-test:1.0 .

docker-compose up -d
