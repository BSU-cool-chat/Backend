package project.service.Message;

import project.models.Message;

import java.util.List;

public interface MessageService {
    List<Message> getAllMessages(int chat_id);

    void createMessage(Message message);
}