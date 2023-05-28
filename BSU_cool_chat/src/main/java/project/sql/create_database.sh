#!/bin/bash

database="bsu_cool_chat_db"

psql -U postgres -h localhost -c "DROP DATABASE IF EXISTS ${database};" -c "CREATE DATABASE ${database};"
psql -U postgres -h localhost bsu_cool_chat_db < create_tables.sql

Green='\033[0;32m'
echo -e "${Green}Create ${database} -- all right!\n"
