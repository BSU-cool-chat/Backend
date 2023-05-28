INSERT INTO users VALUES  (0, 'adamenko', 'qwerty');
INSERT INTO users_info VALUES  (0, 0, 'adamenko', 'male', 19, 'YSDA 1st year student, BSU 2nd year student, swimmer');
INSERT INTO users VALUES  (1, 'zhdun', '123456');
INSERT INTO users_info VALUES  (1, 1, 'zhdun', 'male', 15, 'schoolboy');
INSERT INTO users VALUES  (2, 'adamada', 'rftgyhyuj');
INSERT INTO users_info VALUES  (2, 2, 'adamada', 'male', 3, 'baby');

INSERT INTO chats VALUES  (0, 'Chat between adamenko and zhdun', 'f');
INSERT INTO chats VALUES  (1, 'Chat between adamenko and adamada', 'f');
INSERT INTO chats VALUES  (2, 'Chat between zhdun and adamada', 'f');

INSERT INTO chats_members VALUES  (0, 0);
INSERT INTO chats_members VALUES  (0, 1);
INSERT INTO chats_members VALUES  (1, 0);
INSERT INTO chats_members VALUES  (1, 2);
INSERT INTO chats_members VALUES  (2, 1);
INSERT INTO chats_members VALUES  (2, 2);

INSERT INTO messages VALUES  (0, 0, 0, 'Hi! How are you?', TO_TIMESTAMP(1685122296));
INSERT INTO messages VALUES  (1, 1, 0, 'I am OK. And you?', TO_TIMESTAMP(1685133929));
INSERT INTO messages VALUES  (2, 0, 0, 'I am fine. What are doing tonight?', TO_TIMESTAMP(1685133965));
INSERT INTO messages VALUES  (3, 1, 0, 'I am coding a project:)', TO_TIMESTAMP(1685134200));

INSERT INTO messages VALUES  (4, 2, 1, '1', TO_TIMESTAMP(1685134281));
INSERT INTO messages VALUES  (5, 0, 1, '2', TO_TIMESTAMP(1685134282));
INSERT INTO messages VALUES  (6, 2, 1, '3', TO_TIMESTAMP(1685134283));
INSERT INTO messages VALUES  (7, 0, 1, '4', TO_TIMESTAMP(1685134284));

INSERT INTO messages VALUES  (8, 2, 2, 'Wa', TO_TIMESTAMP(1685134378));
INSERT INTO messages VALUES  (9, 1, 2, 'Ok', TO_TIMESTAMP(1685134379));
INSERT INTO messages VALUES  (10, 2, 2, 'Re', TO_TIMESTAMP(1685134382));
INSERT INTO messages VALUES  (11, 1, 2, 'Pe', TO_TIMESTAMP(1685134390));