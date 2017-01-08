#!/usr/bin/env bash

set -e -x

pushd bship
  service mysql start
  ./gradlew clean build
popd