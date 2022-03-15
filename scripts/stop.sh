#!/usr/bin/env bash

ABSPATH=$(readlink -f $0) # 현재파일 절대경로 (링크가 있다면 실제 경로를 찾도록한다)
ABSDIR=$(dirname $ABSPATH) # ABSPATH가 있는 디렉토리 (ABSPATH는 파일명이 포함된 경로이기때문)
source ${ABSDIR}/profile.sh # profile.sh를 실행한다.

IDLE_PORT=$(find_idle_port)

CONTAINER_NAME=$(sudo docker ps -aqf "name=${IDLE_PORT}")

echo ">$CONTAINER_NAME"

if [ -z ${CONTAINER_NAME} ]
then
        echo ">구동중인 컨테이너가 없습니다."
else
        echo ">docker rm -f $CONTAINER_NAME"
        sudo docker rm -f ${CONTAINER_NAME}
        sleep 5
fi
