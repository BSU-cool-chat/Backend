package project.dao.Message;

import project.models.Message;

public interface MessageService {
    Iterable<Message> getAllMessages();
    void createMessage(Message customer);
    void deleteMessage(int id);
    void updateMessage(Message customer);
}