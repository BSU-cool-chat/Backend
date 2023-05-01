package project.dao.Chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import project.models.ChatInfo;

import java.util.List;

@Component
public class ChatDAO implements ChatService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ChatDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ChatInfo> getAllChatsInfo(int id) {
        return jdbcTemplate.query(
                """
                        SELECT senders.login AS sender_login, receivers.login AS receiver_login, sender_id, receiver_id,
                            text, dispatch_time, interlocutor
                        FROM
                            (SELECT interlocutor, MAX(dispatch_time) AS max_time
                            FROM (
                                SELECT DISTINCT receiver_id AS interlocutor
                                FROM messages
                                WHERE sender_id = ?
                                UNION DISTINCT
                                SELECT DISTINCT sender_id AS interlocutor
                                FROM messages
                                WHERE receiver_id = ?
                                ) interlocutors
                                INNER JOIN messages ON (sender_id = ? AND receiver_id = interlocutor)
                                                        OR (sender_id = interlocutor AND receiver_id = ?)
                            GROUP BY interlocutor) last_messages
                            INNER JOIN messages ON ((sender_id = ? AND receiver_id = interlocutor)
                                                    OR (sender_id = interlocutor AND receiver_id = ?))
                                                    AND dispatch_time = max_time
                            INNER JOIN users AS senders ON senders.id = messages.sender_id
                            INNER JOIN users AS receivers ON receivers.id = messages.receiver_id;""",
                new ChatInfoMapper(),
                id, id, id, id, id, id).stream().toList();
    }
}
