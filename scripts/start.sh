#!/bin/bash

echo "> Run Jar"

nohup java -jar -Dspring.config.location=/home/ec2-user/toy-project-board/application-db.yml $JAR_PATH/demo-0.0.1-SNAPSHOT.jar > $JAR_PATH/nohup.out 2>&1 &