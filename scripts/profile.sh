#!/usr/bin/env bash

function find_idle_profile()
{
    # 현재 활성화된 profile 찾기
    RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

    # 400 보다 크면(에러)다면
    if [ ${RESPONSE_CODE} -ge 400 ]
    then
        CURRENT_PROFILE=real2 # 현재 활성화된 profile이 없다면 real2로 매핑한다
    else
        CURRENT_PROFILE=$(curl -s http://localhost/profile) # 현재 활성화된 profile
    fi


    if [ ${CURRENT_PROFILE} == real1 ] # 현재 활성화된 profile이 real1이면
    then
      IDLE_PROFILE=real2 # real2가 쉬는중
    else
      IDLE_PROFILE=real1
    fi

    echo "${IDLE_PROFILE}"
}

# 쉬고 있는 profile의 port 찾기
function find_idle_port()
{
    IDLE_PROFILE=$(find_idle_profile) # 현재 쉬고 있는 profile

    if [ ${IDLE_PROFILE} == real1 ]
    then
      echo "8081"
    else
      echo "8082"
    fi
}