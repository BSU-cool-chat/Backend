#!/bin/bash

database="bsu_cool_chat_db"

sudo -u postgres psql -d $database -U postgres -c "INSERT INTO users VALUES  (0, 'adamenko', 'qwerty');"
sudo -u postgres psql -d $database -U postgres -c "INSERT INTO users_info VALUES  (0, 0, 'adamenko', 'male', 19, 'YSDA 1st year student, BSU 2nd year student, swimmer');"

sudo -u postgres psql -d $database -U postgres -c "INSERT INTO users VALUES  (1, 'zhdun', '123456');"
sudo -u postgres psql -d $database -U postgres -c "INSERT INTO users_info VALUES  (1, 1, 'zhdun', 'male', 15, 'schoolboy');"

sudo -u postgres psql -d $database -U postgres -c "INSERT INTO users VALUES  (2, 'adamada', 'rftgyhyuj');"
sudo -u postgres psql -d $database -U postgres -c "INSERT INTO users_info VALUES  (2, 2, 'adamada', 'male', 3, 'baby');"

Green='\033[0;32m'
echo -e "${Green}Add users ${database} -- all right!\n"
