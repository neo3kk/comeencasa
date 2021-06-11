#!/bin/bash

sudo cp -r  ../Dev/FrontEnd/dist/pwa/** htdocs/

cp ../Dev/BackEnd/target/comeencasa-1.jar comeencasa.jar


#ONLY FOR  UPDATE  IMAGE DOCKER

sudo docker build --no-cache -t neo3kk/comeencasa .

sudo docker push neo3kk/comeencasa:latest

sudo docker-compose up



#

