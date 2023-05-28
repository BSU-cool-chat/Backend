#!/bin/bash

database="bsu_cool_chat_db"

sudo -u postgres psql -d $database -U postgres < add_users.sql
sudo -u postgres psql -d $database -U postgres < add_messages.sql

Green='\033[0;32m'
echo -e "${Green}Add test info ${database} -- all right!\n"

