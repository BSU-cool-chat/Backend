#!/bin/bash

database="bsu_cool_chat_db"
password=$1

echo ${password} | sudo -S -u postgres psql -d $database -U postgres -c "INSERT INTO chats VALUES  (1, 'Chat between adamenko and zhdun', 'f');"
echo ${password} | sudo -S -u postgres psql -d $database -U postgres -c "INSERT INTO chats VALUES  (2, 'Chat between adamenko and adamada', 'f');"
echo ${password} | sudo -S -u postgres psql -d $database -U postgres -c "INSERT INTO chats VALUES  (3, 'Chat between zhdun and adamada', 'f');"

echo ${password} | sudo -S -u postgres psql -d $database -U postgres -c "INSERT INTO chats_members VALUES  (1, 1);"
echo ${password} | sudo -S -u postgres psql -d $database -U postgres -c "INSERT INTO chats_members VALUES  (1, 2);"
echo ${password} | sudo -S -u postgres psql -d $database -U postgres -c "INSERT INTO chats_members VALUES  (2, 1);"
echo ${password} | sudo -S -u postgres psql -d $database -U postgres -c "INSERT INTO chats_members VALUES  (2, 3);"
echo ${password} | sudo -S -u postgres psql -d $database -U postgres -c "INSERT INTO chats_members VALUES  (3, 2);"
echo ${password} | sudo -S -u postgres psql -d $database -U postgres -c "INSERT INTO chats_members VALUES  (3, 3);"

echo ${password} | sudo -S -u postgres psql -d $database -U postgres -c "INSERT INTO messages VALUES  (1, 1, 1, 'Hi! How are you?', TO_TIMESTAMP(1685122296));"
echo ${password} | sudo -S -u postgres psql -d $database -U postgres -c "INSERT INTO messages VALUES  (2, 2, 1, 'I am OK. And you?', TO_TIMESTAMP(1685133929));"
echo ${password} | sudo -S -u postgres psql -d $database -U postgres -c "INSERT INTO messages VALUES  (3, 1, 1, 'I am fine. What are doing tonight?', TO_TIMESTAMP(1685133965));"
echo ${password} | sudo -S -u postgres psql -d $database -U postgres -c "INSERT INTO messages VALUES  (4, 2, 1, 'I am coding a project:)', TO_TIMESTAMP(1685134200));"

echo ${password} | sudo -S -u postgres psql -d $database -U postgres -c "INSERT INTO messages VALUES  (5, 3, 2, '1', TO_TIMESTAMP(1685134281));"
echo ${password} | sudo -S -u postgres psql -d $database -U postgres -c "INSERT INTO messages VALUES  (6, 1, 2, '2', TO_TIMESTAMP(1685134282));"
echo ${password} | sudo -S -u postgres psql -d $database -U postgres -c "INSERT INTO messages VALUES  (7, 3, 2, '3', TO_TIMESTAMP(1685134283));"
echo ${password} | sudo -S -u postgres psql -d $database -U postgres -c "INSERT INTO messages VALUES  (8, 1, 2, '4', TO_TIMESTAMP(1685134284));"

echo ${password} | sudo -S -u postgres psql -d $database -U postgres -c "INSERT INTO messages VALUES  (9, 3, 3, 'Wa', TO_TIMESTAMP(1685134378));"
echo ${password} | sudo -S -u postgres psql -d $database -U postgres -c "INSERT INTO messages VALUES  (10, 2, 3, 'Ok', TO_TIMESTAMP(1685134379));"
echo ${password} | sudo -S -u postgres psql -d $database -U postgres -c "INSERT INTO messages VALUES  (11, 3, 3, 'Re', TO_TIMESTAMP(1685134382));"
echo ${password} | sudo -S -u postgres psql -d $database -U postgres -c "INSERT INTO messages VALUES  (12, 2, 3, 'Pe', TO_TIMESTAMP(1685134390));"

Green='\033[0;32m'
echo -e "${Green}Add users ${database} -- all right!\n"
