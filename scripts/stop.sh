#!/usr/bin/env bash

ABSPATH=$(readlink -f $0) # 현재파일 절대경로 (링크가 있다면 실제 경로를 찾도록한다)
ABSDIR=$(dirname $ABSPATH) # ABSPATH가 있는 디렉토리 (ABSPATH는 파일명이 포함된 경로이기때문)
source ${ABSDIR}/profile.sh # profile.sh를 실행한다.

IDLE_PORT=$(find_idle_port)

echo "> $IDLE_PORT 에서 구동중인 애플리케이션 pid 확인"
IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})

if [ -z ${IDLE_PID} ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $IDLE_PID"
  kill -15 ${IDLE_PID}
  sleep 5
fi