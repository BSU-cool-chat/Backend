package project.dao.Chat;

import project.models.Chat;
import project.models.ChatInfo;
import project.models.Message;
import project.models.User;

import java.util.List;

public interface ChatDAO {

    List<Chat> getAllUsersChats(int user_id);

    Chat getChat(int chat_id);
    void deleteChat(int chat_id);

    Chat getOrCreateStandardChat(User user1, User user2);

    List<Integer> getAllChatMembers(int chat_id);
}
