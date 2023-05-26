#!/bin/bash

database="bsu_cool_chat"
sudo -u postgres psql -U postgres -c "DROP DATABASE IF EXISTS bsu_cool_chat;"

sudo -u postgres psql -U postgres -c "CREATE DATABASE bsu_cool_chat;"


sudo -u postgres psql -d $database -U postgres -c "CREATE TABLE IF NOT EXISTS users(
                                                      id SERIAL PRIMARY KEY NOT NULL,
                                                      login varchar NOT NULL,
                                                      password varchar NOT NULL);"


sudo -u postgres psql -d $database -U postgres -c " CREATE TABLE IF NOT EXISTS chats(
                                                      id SERIAL PRIMARY KEY NOT NULL,
                                                      name varchar NOT NULL,
                                                      is_group_chat boolean NOT NULL);"


sudo -u postgres psql -d $database -U postgres -c "CREATE TABLE IF NOT EXISTS chats_members(
                                                      chat_id int NOT NULL,
                                                      member_id int NOT NULL,
                                                      FOREIGN KEY (chat_id) REFERENCES chats (id) ON DELETE CASCADE,
                                                      FOREIGN KEY (member_id) REFERENCES users (id) ON DELETE CASCADE);"

sudo -u postgres psql -d $database -U postgres -c "CREATE TABLE IF NOT EXISTS messages(
                                                      id SERIAL PRIMARY KEY NOT NULL,
                                                      sender_id int NOT NULL,
                                                      chat_id int NOT NULL,
                                                      text varchar NOT NULL,
                                                      dispatch_time timestamptz NOT NULL,
                                                      FOREIGN KEY (chat_id) REFERENCES chats (id) ON DELETE CASCADE,
                                                      FOREIGN KEY (sender_id) REFERENCES users (id) ON DELETE CASCADE);"

sudo -u postgres psql -d $database -U postgres -c " CREATE TABLE IF NOT EXISTS users_info(
                                                      id SERIAL PRIMARY KEY NOT NULL,
                                                      user_id int NOT NULL,
                                                      name varchar,
                                                      sex varchar,
                                                      age int,
                                                      additional_info varchar,
                                                      FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE);"

Green='\033[0;32m'
echo -e "${Green}All right!\n"
