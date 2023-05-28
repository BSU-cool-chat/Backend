INSERT INTO users(login, password)
VALUES ('adamenko', 'qwerty'),
       ('zhdun', '123456'),
       ('adamada', 'rftgyhyuj');

INSERT INTO users_info(user_id, name, sex, age, additional_info)
VALUES ((SELECT id FROM users WHERE login = 'adamenko'), 'adamenko', 'male', 19,
        'YSDA 1st year student, BSU 2nd year student, swimmer'),
       ((SELECT id FROM users WHERE login = 'zhdun'), 'zhdun', 'male', 15, 'schoolboy'),
       ((SELECT id FROM users WHERE login = 'adamada'), 'adamada', 'male', 3, 'baby');
