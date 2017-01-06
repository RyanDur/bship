#!/usr/bin/env bash

set -e -x

pushd bship
  mysql -u root
  CREATE DATABASE bs;
  exit;
  ./gradlew clean build
  mv ./build/libs/bship-1.0-SNAPSHOT.jar ../compiled-jar
popd


