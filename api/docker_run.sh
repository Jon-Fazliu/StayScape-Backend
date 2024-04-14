#!/bin/bash
cd /application || exit 1
SPRING_PROFILES_ACTIVE=prod java -jar $1 -Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8
