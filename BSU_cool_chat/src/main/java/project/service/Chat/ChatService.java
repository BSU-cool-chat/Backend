package project.service.Chat;

import project.models.Chat;
import project.models.ChatInfo;

import java.util.List;

public interface ChatService {
    List<Chat> getAllUserChats(int user_id);

    List<ChatInfo> getAllUsersChatsInfo(int user_id);

    Chat getChat(int chat_id);
    Chat getOrCreateStandardChat(int user1_id, int user2_id);
}
