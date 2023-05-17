package project.dao.Chat;

import project.models.Chat;
import project.models.ChatInfo;

import java.util.List;

public interface ChatService {
    List<Chat> getAllUsersChats(int user_id);
    List<ChatInfo> getAllUsersChatsInfo(int user_id);
}
