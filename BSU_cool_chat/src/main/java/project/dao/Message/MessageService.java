package project.dao.Message;

import project.models.Message;
import project.models.ChatInfo;

import java.util.List;

public interface MessageService {
    List<Message> getAllMessages();
    void createMessage(Message customer);
    void deleteMessage(int id);
    void updateMessage(Message customer);
}