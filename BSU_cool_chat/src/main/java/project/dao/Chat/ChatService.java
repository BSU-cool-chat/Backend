package project.dao.Chat;

import project.models.Chat;
import project.models.ChatInfo;
import project.models.Message;

import java.util.List;

public interface ChatService {
    List<Chat> getAllUsersChats(int user_id);

    List<ChatInfo> getAllUsersChatsInfo(int user_id);

    Chat getChat(int chat_id);
    Chat getOrCreateStandardChat(int user1_id, int user2_id);

    void createMessage(Message message);
}
