CREATE TABLE IF NOT EXISTS users
(
    id
    SERIAL
    PRIMARY
    KEY
    NOT
    NULL,
    login
    varchar
    NOT
    NULL,
    password
    varchar
    NOT
    NULL
);

CREATE TABLE IF NOT EXISTS chats
(
    id
    SERIAL
    PRIMARY
    KEY
    NOT
    NULL,
    name
    varchar
    NOT
    NULL,
    is_group_chat
    boolean
    NOT
    NULL
);

CREATE TABLE IF NOT EXISTS chats_members
(
    chat_id
    int
    NOT
    NULL,
    member_id
    int
    NOT
    NULL,
    FOREIGN
    KEY
(
    chat_id
) REFERENCES chats
(
    id
) ON DELETE CASCADE,
    FOREIGN KEY
(
    member_id
) REFERENCES users
(
    id
)
  ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS messages
(
    id
    SERIAL
    PRIMARY
    KEY
    NOT
    NULL,
    sender_id
    int
    NOT
    NULL,
    chat_id
    int
    NOT
    NULL,
    text
    varchar
    NOT
    NULL,
    dispatch_time
    timestamptz
    NOT
    NULL,
    FOREIGN
    KEY
(
    chat_id
) REFERENCES chats
(
    id
) ON DELETE CASCADE,
    FOREIGN KEY
(
    sender_id
) REFERENCES users
(
    id
)
  ON DELETE CASCADE);


CREATE TABLE IF NOT EXISTS users_info
(
    id
    SERIAL
    PRIMARY
    KEY
    NOT
    NULL,
    user_id
    int
    NOT
    NULL,
    name
    varchar,
    sex
    varchar,
    age
    int,
    additional_info
    varchar,
    FOREIGN
    KEY
(
    user_id
) REFERENCES users
(
    id
) ON DELETE CASCADE);
