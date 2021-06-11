#!/bin/bash

#BUILD PWA APPLICATION FROM LASTEST VERSION OF FRONTEND

cd ../Dev/FrontEnd
quasar build -m pwa
cp -r  dist/pwa/** ../../Prod/htdocs

cp ../BackEnd/target/comeencasa-1.jar ../../Prod/comeencasa.jar

cd ../../Prod/

#ONLY FOR  UPDATE  IMAGE DOCKER

#sudo docker build --no-cache -t neo3kk/comeencasa .

#sudo docker push neo3kk/comeencasa:latest

sudo docker-compose up	



#

