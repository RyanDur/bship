#!/usr/bin/env bash

set -e -x

pushd bship
  ./gradlew clean build
popd