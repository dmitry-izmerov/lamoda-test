#!/bin/sh

cd ../ && mvn clean package
cp ./target/ROOT.war ./docker/ROOT.war
cd ./docker

docker build -t lamoda-test:1.0

docker run --name my-postgres -v /$(pwd)/_tmp_data:/var/lib/postgresql/data \
	-e POSTGRES_PASSWORD=p@ss \
	-e POSTGRES_USER=postgres \
	-d postgres:9.6

docker run --name lamoda-test-app --link my-postgres:postgres -d \
	-p 8080:8080 \
	-e POSTGRES_PASSWORD=p@ss \
	-e POSTGRES_URL=jdbc:postgresql://localhost:5432/lamodatest \
	-e POSTGRES_USER=postgres \
	lamoda-test:1.0

