#!/usr/bin/env bash

set -e -x

pushd bship
  mv ./build/libs/bship-1.0-SNAPSHOT.jar ../compiled-jar
popd


