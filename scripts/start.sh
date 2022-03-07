#!/usr/bin/env bash

JAR_PATH=/home/ec2-user/toy-project-board/deploy
PROJECT_PATH=/home/ec2-user/toy-project-board/deploy/project
ABSPATH=$(readlink -f $0) # 현재파일이 속한 심볼릭 링크를 끝까지 탐색한 결과를 반환한다.
ABSDIR=$(dirname $ABSPATH) # 현재 파일이 속한 경로를 반환한다
source ${ABSDIR}/profile.sh # profile.sh를 실행한다.


echo "> Start Build"

cd $PROJECT_PATH # 프로젝트가 저장된 경로로 이동
sudo chmod ugo+rwx gradlew # 권한부여
sudo ./gradlew build # 빌드시작

echo "> copy Jar"
cp $PROJECT_PATH/build/libs/*jar $JAR_PATH/ # jar 파일을 복사

JAR_NAME=$(ls -tr $JAR_PATH/*.jar | tail -n 1) # 실행할 Jar명 가져오기

echo "> Run $JAR_NAME"

IDLE_PROFILE=$(find_idle_profile)

echo ">Run $JAR_NAME with IDLE_PROFILE"
echo ">IDLE_PROFILE $IDLE_PROFILE"

# application-db.yml 로딩 및 $IDLE_PROFILE profile 선택
nohup java -jar -Dspring.config.location=/home/ec2-user/toy-project-board/application-db.yml,/home/ec2-user/toy-project-board/application-$IDLE_PROFILE.yml -Dspring.profiles.active=$IDLE_PROFILE $JAR_NAME > $JAR_PATH/nohup.out 2>&1 &

