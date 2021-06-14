#!/bin/bash

cd ./FrontEnd
docker-compose run node npm install
docker-compose up -d node
cd ../BackEnd
docker-compose up -d
