#!/bin/bash

database="bsu_cool_chat_db"

sudo -u postgres psql -U postgres -c "DROP DATABASE IF EXISTS ${database};"

Green='\033[0;32m'
echo -e "${Green}Clear database ${database} -- all right!\n"
