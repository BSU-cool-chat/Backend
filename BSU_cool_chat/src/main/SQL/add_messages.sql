INSERT INTO chats(name, is_group_chat)
VALUES ('Chat between adamenko and zhdun', 'f'),
       ('Chat between adamenko and adamada', 'f'),
       ('Chat between zhdun and adamada', 'f');

INSERT INTO chats_members(chat_id, member_id)
VALUES ((SELECT id FROM chats WHERE name = 'Chat between adamenko and zhdun'),
        (SELECT id FROM users WHERE login = 'adamenko')),
       ((SELECT id FROM chats WHERE name = 'Chat between adamenko and zhdun'),
        (SELECT id FROM users WHERE login = 'zhdun')),
       ((SELECT id FROM chats WHERE name = 'Chat between adamenko and adamada'),
        (SELECT id FROM users WHERE login = 'adamenko')),
       ((SELECT id FROM chats WHERE name = 'Chat between adamenko and adamada'),
        (SELECT id FROM users WHERE login = 'adamada')),
       ((SELECT id FROM chats WHERE name = 'Chat between zhdun and adamada'),
        (SELECT id FROM users WHERE login = 'zhdun')),
       ((SELECT id FROM chats WHERE name = 'Chat between zhdun and adamada'),
        (SELECT id FROM users WHERE login = 'adamada'));

INSERT INTO messages(sender_id, chat_id, text, dispatch_time)
VALUES ((SELECT id FROM users WHERE login = 'adamenko'),
        (SELECT id FROM chats WHERE name = 'Chat between adamenko and zhdun'), 'Hi! How are you?',
        TO_TIMESTAMP(1685122296)),
       ((SELECT id FROM users WHERE login = 'zhdun'),
        (SELECT id FROM chats WHERE name = 'Chat between adamenko and zhdun'), 'I am OK. And you?',
        TO_TIMESTAMP(1685133929)),
       ((SELECT id FROM users WHERE login = 'adamenko'),
        (SELECT id FROM chats WHERE name = 'Chat between adamenko and zhdun'), 'I am fine. What are doing tonight?',
        TO_TIMESTAMP(1685133965)),
       ((SELECT id FROM users WHERE login = 'zhdun'),
        (SELECT id FROM chats WHERE name = 'Chat between adamenko and zhdun'), 'I am coding a project:)',
        TO_TIMESTAMP(1685134200));

INSERT INTO messages(sender_id, chat_id, text, dispatch_time)
VALUES ((SELECT id FROM users WHERE login = 'adamada'),
        (SELECT id FROM chats WHERE name = 'Chat between adamenko and adamada'), '1', TO_TIMESTAMP(1685134281)),
       ((SELECT id FROM users WHERE login = 'adamenko'),
        (SELECT id FROM chats WHERE name = 'Chat between adamenko and adamada'), '2', TO_TIMESTAMP(1685134282)),
       ((SELECT id FROM users WHERE login = 'adamada'),
        (SELECT id FROM chats WHERE name = 'Chat between adamenko and adamada'), '3', TO_TIMESTAMP(1685134283)),
       ((SELECT id FROM users WHERE login = 'adamenko'),
        (SELECT id FROM chats WHERE name = 'Chat between adamenko and adamada'), '4', TO_TIMESTAMP(1685134284));

INSERT INTO messages(sender_id, chat_id, text, dispatch_time)
VALUES ((SELECT id FROM users WHERE login = 'adamada'),
        (SELECT id FROM chats WHERE name = 'Chat between zhdun and adamada'), 'Wa', TO_TIMESTAMP(1685134378)),
       ((SELECT id FROM users WHERE login = 'zhdun'),
        (SELECT id FROM chats WHERE name = 'Chat between zhdun and adamada'), 'Ok', TO_TIMESTAMP(1685134379)),
       ((SELECT id FROM users WHERE login = 'adamada'),
        (SELECT id FROM chats WHERE name = 'Chat between zhdun and adamada'), 'Re', TO_TIMESTAMP(1685134382)),
       ((SELECT id FROM users WHERE login = 'zhdun'),
        (SELECT id FROM chats WHERE name = 'Chat between zhdun and adamada'), 'Pe', TO_TIMESTAMP(1685134390));
