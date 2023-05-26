#!/bin/bash

database="bsu_cool_chat_db"
password=$1

echo ${password} | sudo -S -u postgres psql -U postgres -c "DROP DATABASE IF EXISTS ${database};"

Green='\033[0;32m'
echo -e "${Green}Clear database ${database} -- all right!\n"
