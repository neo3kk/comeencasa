#!/bin/bash

#BUILD PWA APPLICATION FROM LASTEST VERSION OF FRONTEND

cd ../Dev/FrontEnd

docker-compose run node npm install

sudo docker-compose run nodePWA