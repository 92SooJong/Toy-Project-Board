#!/bin/bash

JAR_PATH=/home/ec2-user/toy-project-board/deploy
PROJECT_PATH=/home/ec2-user/toy-project-board/deploy/project


echo "> change gradlew Permission to Excutable"
sudo chmod u+x $PROJECT_PATH/gradlew

echo "> Start Build"

$PROJECT_PATH/gradlew build

echo "> copy Jar"
cp $PROJECT_PATH/build/libs/*jar $JAR_PATH/

echo "> Run Jar"
nohup java -jar -Dspring.config.location=/home/ec2-user/toy-project-board/application-db.yml $JAR_PATH/demo-0.0.1-SNAPSHOT.jar > $JAR_PATH/nohup.out 2>&1 &