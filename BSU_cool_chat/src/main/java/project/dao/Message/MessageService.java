package project.dao.Message;

import project.models.Message;
import project.models.ChatInfo;

import java.util.List;

public interface MessageService {
    //    List<Message> getAllMessages();
    List<Message> getAllMessages(int chat_id);
//    void createMessage(Message message);
//    void deleteMessage(int id);
//    void updateMessage(Message message);
}