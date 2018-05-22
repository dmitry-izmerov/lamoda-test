#!/bin/sh

docker container stop my-postgres lamoda-test-app
docker container rm my-postgres lamoda-test-app
rm ROOT.war