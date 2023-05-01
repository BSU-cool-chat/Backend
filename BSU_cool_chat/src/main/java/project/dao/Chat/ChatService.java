package project.dao.Chat;

import project.models.ChatInfo;

import java.util.List;

public interface ChatService {
    List<ChatInfo> getAllChatsInfo(int id);
}
