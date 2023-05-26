package project.dao.Message;

import project.models.Message;

import java.util.List;

public interface MessageDAO {
    List<Message> getAllMessages(int chat_id);

    void createMessage(Message message);
}
