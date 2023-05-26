package project.dao.Chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import project.dao.Message.MessageDAO;
import project.dao.Message.MessageService;
import project.dao.User.UserService;
import project.models.Chat;
import project.models.ChatInfo;
import project.models.Message;
import project.models.User;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class ChatDAO implements ChatService {
    private final JdbcTemplate jdbcTemplate;
    private final MessageService messageService;
    private final UserService userService;

    @Autowired
    public ChatDAO(JdbcTemplate jdbcTemplate, MessageDAO messageService, UserService userService) {
        this.jdbcTemplate = jdbcTemplate;
        this.messageService = messageService;
        this.userService = userService;
    }

    private void FillChatWithMessagesAndMembers(Chat chat) {
        chat.setMessages(messageService.getAllMessages(chat.getId()));
        chat.setMembers(userService.getAllChatMembers(chat.getId()));
    }

    @Override
    public List<Chat> getAllUsersChats(int user_id) {
        var chats = new java.util.ArrayList<>(jdbcTemplate.query("""
                SELECT chat_id, name, is_group_chat
                FROM chats_members
                        JOIN chats ON chat_id = id
                WHERE member_id = ?;""", new ChatMapper(), user_id).stream().toList());
        chats.forEach(this::FillChatWithMessagesAndMembers);
        return chats;
    }

    @Override
    public List<ChatInfo> getAllUsersChatsInfo(int user_id) {
        return getAllUsersChats(user_id).stream().filter(chat -> !chat.getMessages().isEmpty()).map(chat -> chat.getChatInfo()).toList();
    }

    @Override
    public Chat getChat(int chat_id) {
        Optional<Chat> chat = jdbcTemplate.query("""
                SELECT id AS chat_id, name, is_group_chat
                FROM chats
                WHERE id = ?;""", new ChatMapper(), chat_id).stream().findAny();
        if (chat.isEmpty()) {
            throw new RuntimeException("Chat not found");
        }
        FillChatWithMessagesAndMembers(chat.get());
        return chat.get();
    }

    @Override
    public Chat getOrCreateStandardChat(int user1_id, int user2_id) {
        if (user1_id == user2_id) {
            throw new RuntimeException("Cannot create chat between the same users");
        }
        var searching_result = jdbcTemplate.query("""
                SELECT id AS chat_id, name, is_group_chat
                FROM    (SELECT *
                        FROM chats_members
                        WHERE member_id = ?) q1
                        INNER JOIN
                        (SELECT *
                        FROM chats_members
                        WHERE member_id = ?) q2
                        ON q1.chat_id = q2.chat_id
                        INNER JOIN chats ON q1.chat_id = chats.id
                WHERE is_group_chat = false;""", new ChatMapper(), user1_id, user2_id).stream().findAny();
        return searching_result.orElseGet(() -> CreateStandardChat(user1_id, user2_id));
    }

    @Override
    public void createMessage(Message message) {
        messageService.createMessage(message);
    }

    private Chat CreateStandardChat(int user1_id, int user2_id) {
        User user1 = userService.getUser(user1_id);
        User user2 = userService.getUser(user2_id);
        String chat_name = "Chat between " + user1.getLogin() + " and " + user2.getLogin();
        int chat_id = jdbcTemplate.query("""
                INSERT INTO chats(name, is_group_chat)
                VALUES (?, ?)
                RETURNING id""", new IdMapper(), chat_name, false).stream().findAny().get();
        jdbcTemplate.update("""
                INSERT INTO chats_members(chat_id, member_id)
                VALUES (?, ?), (?, ?)""", chat_id, user1_id, chat_id, user2_id);
        Chat new_chat = new Chat(chat_id, chat_name, false);
        new_chat.setMembers(List.of(user1, user2));
        return new_chat;
    }
}