#!/bin/bash

database="bsu_cool_chat_db"

sudo -u postgres psql -U postgres < clear_database.sql

sudo -S -u postgres psql -U postgres -c "CREATE DATABASE ${database};"

sudo -u postgres psql -d $database -U postgres < empty_database.sql

Green='\033[0;32m'
echo -e "${Green}Init empty ${database} -- all right!\n"

