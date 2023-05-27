#!/bin/bash

database="bsu_cool_chat_db"

sudo -u postgres psql -d $database -U postgres -c "INSERT INTO chats VALUES  (0, 'Chat between adamenko and zhdun', 'f');"
sudo -u postgres psql -d $database -U postgres -c "INSERT INTO chats VALUES  (1, 'Chat between adamenko and adamada', 'f');"
sudo -u postgres psql -d $database -U postgres -c "INSERT INTO chats VALUES  (2, 'Chat between zhdun and adamada', 'f');"

sudo -u postgres psql -d $database -U postgres -c "INSERT INTO chats_members VALUES  (0, 0);"
sudo -u postgres psql -d $database -U postgres -c "INSERT INTO chats_members VALUES  (0, 1);"
sudo -u postgres psql -d $database -U postgres -c "INSERT INTO chats_members VALUES  (1, 0);"
sudo -u postgres psql -d $database -U postgres -c "INSERT INTO chats_members VALUES  (1, 2);"
sudo -u postgres psql -d $database -U postgres -c "INSERT INTO chats_members VALUES  (2, 1);"
sudo -u postgres psql -d $database -U postgres -c "INSERT INTO chats_members VALUES  (2, 2);"

sudo -u postgres psql -d $database -U postgres -c "INSERT INTO messages VALUES  (0, 0, 0, 'Hi! How are you?', TO_TIMESTAMP(1685122296));"
sudo -u postgres psql -d $database -U postgres -c "INSERT INTO messages VALUES  (1, 1, 0, 'I am OK. And you?', TO_TIMESTAMP(1685133929));"
sudo -u postgres psql -d $database -U postgres -c "INSERT INTO messages VALUES  (2, 0, 0, 'I am fine. What are doing tonight?', TO_TIMESTAMP(1685133965));"
sudo -u postgres psql -d $database -U postgres -c "INSERT INTO messages VALUES  (3, 1, 0, 'I am coding a project:)', TO_TIMESTAMP(1685134200));"

sudo -u postgres psql -d $database -U postgres -c "INSERT INTO messages VALUES  (4, 2, 1, '1', TO_TIMESTAMP(1685134281));"
sudo -u postgres psql -d $database -U postgres -c "INSERT INTO messages VALUES  (5, 0, 1, '2', TO_TIMESTAMP(1685134282));"
sudo -u postgres psql -d $database -U postgres -c "INSERT INTO messages VALUES  (6, 2, 1, '3', TO_TIMESTAMP(1685134283));"
sudo -u postgres psql -d $database -U postgres -c "INSERT INTO messages VALUES  (7, 0, 1, '4', TO_TIMESTAMP(1685134284));"

sudo -u postgres psql -d $database -U postgres -c "INSERT INTO messages VALUES  (8, 2, 2, 'Wa', TO_TIMESTAMP(1685134378));"
sudo -u postgres psql -d $database -U postgres -c "INSERT INTO messages VALUES  (9, 1, 2, 'Ok', TO_TIMESTAMP(1685134379));"
sudo -u postgres psql -d $database -U postgres -c "INSERT INTO messages VALUES  (10, 2, 2, 'Re', TO_TIMESTAMP(1685134382));"
sudo -u postgres psql -d $database -U postgres -c "INSERT INTO messages VALUES  (11, 1, 2, 'Pe', TO_TIMESTAMP(1685134390));"

Green='\033[0;32m'
echo -e "${Green}Add messages ${database} -- all right!\n"
