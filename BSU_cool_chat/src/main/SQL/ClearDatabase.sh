#!/bin/bash

database="bsu_cool_chat_db"

sudo -u postgres psql -U postgres < clear_database.sql

Green='\033[0;32m'
echo -e "${Green}Clear database ${database} -- all right!\n"
