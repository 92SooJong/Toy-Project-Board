#!/usr/bin/env bash

PROJECT_PATH=/home/ec2-user/toy-project-board/deploy/project
DOCKER_PATH=/home/ec2-user/toy-project-board/deploy/docker

ABSPATH=$(readlink -f $0) # 현재파일 절대경로 (링크가 있다면 실제 경로를 찾도록한다)
ABSDIR=$(dirname $ABSPATH) # ABSPATH가 있는 디렉토리 (ABSPATH는 파일명이 포함된 경로이기때문)
source ${ABSDIR}/profile.sh # profile.sh를 실행한다.


sudo rm $PROJECT_PATH/build/libs/*jar

echo "> Start Build"

cd $PROJECT_PATH # 프로젝트가 저장된 경로로 이동
sudo chmod ugo+rwx gradlew # 권한부여
sudo ./gradlew build # 빌드시작

sudo rm $DOCKER_PATH/demo*

echo "> copy Jar"
cp $PROJECT_PATH/build/libs/*jar $DOCKER_PATH/ # jar 파일을 복사

echo "> copy Dockerfile"
cp $PROJECT_PATH/Dockerfile $DOCKER_PATH/ # Dockerfile

JAR_NAME=$(ls -tr $DOCKER_PATH/*.jar | tail -n 1) # 실행할 Jar명 가져오기
echo "> Run $JAR_NAME"

# Docker 컨테이너 생성 및 실행 명령어 (run 명령어)
IDLE_PROFILE=$(find_idle_profile)
IDLE_PORT=$(find_idle_port)

echo ">Run $JAR_NAME with IDLE_PROFILE"
echo ">IDLE_PROFILE $IDLE_PROFILE"

# 이미지로 만들 파일 확인
JAR_FILE="${JAR_NAME##*/}"
echo ">JAR_FILE==> $JAR_FILE"

sudo docker build -t toy-project-board:sample --build-arg JAR_FILE=$JAR_FILE $DOCKER_PATH

sudo docker run -d -p 80:${IDLE_PORT} --name ${IDLE_PROFILE} -e "IDLE_PROFILE=${IDLE_PROFILE}" toy-project-board:sample
