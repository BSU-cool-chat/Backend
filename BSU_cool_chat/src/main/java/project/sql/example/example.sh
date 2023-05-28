#!/bin/bash

database="bsu_cool_chat_db"
psql -U postgres -h localhost bsu_cool_chat_db < create.sql

Green='\033[0;32m'
echo -e "${Green}Example ${database} -- all right!\n"
