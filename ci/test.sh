#!/bin/bash

set -e -x

service mysql start


mysql -u root -e "create database $MYSQL_DATABASE;"
mysql -u root -e "GRANT ALL PRIVILEGES ON *.* TO 'root'@'127.0.0.1' IDENTIFIED BY 'password' WITH GRANT OPTION;"

pushd bship
    ./gradlew clean test
popd

