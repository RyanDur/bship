#!/usr/bin/env bash

# this hook is in SCM so that it can be shared
# to install it, create a symbolic link in the projects .git/hooks folder
#
#       i.e. - from the .git/hooks directory, run
#               $ ln -s ../../hooks/pre-push.sh pre-push
#
# to skip the tests, run with the --no-verify argument
#       i.e. - $ 'git commit --no-verify'

# run the tests with the gradle wrapper
./gradlew clean test

# store the last exit code in a variable
RESULT=$?

# return the './gradlew test' exit code
exit $RESULT