#!/bin/bash

CURRENT_PID=$(pgrep -fl demo | grep java | awk '{print $1}')

if [ -z "$CURRENT_PID" ]; then
        echo ">NOT EXIST RUNNING JAR"
else
        echo ">kill -15 $CURRENT_PID"
        kill -15 $CURRENT_PID
        sleep 5
fi