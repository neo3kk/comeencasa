#!/bin/bash

#BUILD PWA APPLICATION FROM LASTEST VERSION OF FRONTEND

sudo ./createDist.sh

sudo cp -r  ../Dev/FrontEnd/dist/pwa/** htdocs/

cp ../Dev/BackEnd/target/comeencasa-1.jar comeencasa.jar


#ONLY CREATE IMAGE FROM DOCKERFILE
sudo docker build --no-cache -t comeencasa .


#ONLY FOR  UPDATE  IMAGE DOCKER

#sudo docker build --no-cache -t neo3kk/comeencasa .
#sudo docker push neo3kk/comeencasa:latest

sudo docker-compose up



#
