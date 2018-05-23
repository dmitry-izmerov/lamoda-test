#!/bin/sh

cd ../ && mvn clean package
cp ./target/ROOT.war ./docker/ROOT.war
cd ./docker

docker build -t lamoda-test:1.0 .

docker run --add-host postgres-host:192.168.5.1 --name my-postgres -v /$(pwd)/_tmp_data:/var/lib/postgresql/data \
	-e POSTGRES_PASSWORD=p@ss \
	-e POSTGRES_USER=postgres \
	-d postgres:9.6

sleep 2

docker run --name lamoda-test-app --link my-postgres:postgres \
    -p 8080:8080 \
	-e POSTGRES_PASSWORD=p@ss \
	-e POSTGRES_URL=jdbc:postgresql://DB_ALIAS/postgres \
	-e POSTGRES_USER=postgres \
	lamoda-test:1.0

