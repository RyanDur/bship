#!/bin/bash

set -e -x

service mysql start

mysql -u root -e "CREATE DATABASE $MYSQL_DATABASE;"
mysql -u root -e "SET GLOBAL max_connections = 2048;"
mysql -u root -e "CREATE USER 'root'@'127.0.0.1';"
mysql -u root -e "GRANT ALL PRIVILEGES ON *.* TO 'root'@'127.0.0.1' WITH GRANT OPTION;"

pushd bship
    ./gradlew clean test &&
popd

service mysql stop