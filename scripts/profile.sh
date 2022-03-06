#!/usr/bin/env bash

function find_idle_profile(){

  # http://localhost/porfile에 요청을 보내서 응답값을 받아온다.
  # 80번 포트는 현재 8080으로 매핑되어 있다.
  RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/porfile)

  if [ ${RESPONSE_CODE} -ge 400 ] # 400보다 응답값이 크면 에러
    then
      CURRENT_PROFILE=real2
    else
      CURRENT_PROFILE=$(curl -s http://localhost/profile)
  fi

  if [ ${CURRENT_PROFILE} == real1]
  then
    IDLE_PROFILE=real2
  else
    IDLE_PROFILE=real1
  fi

  echo "${IDLE_PROFILE}"
}

function find_idle_port(){

  IDLE_PROFILE="$(find_idle_profile)"

  if [ ${IDLE_PROFILE} == real1 ]
  then
    echo "8081"
  else
    echo "8082"
  fi
}
