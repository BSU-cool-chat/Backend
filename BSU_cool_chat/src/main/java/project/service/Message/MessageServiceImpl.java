package project.service.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.dao.Message.MessageDAO;
import project.dao.User.UserDAO;
import project.models.Message;

import java.util.List;

@Component
public class MessageServiceImpl implements MessageService {
    MessageDAO messageDAO;
    UserDAO userDAO;

    @Autowired
    public MessageServiceImpl(MessageDAO messageDAO, UserDAO userDAO) {
        this.messageDAO = messageDAO;
        this.userDAO = userDAO;
    }

    @Override
    public List<Message> getAllMessages(int chat_id) {
        return messageDAO.getAllMessages(chat_id)
                .stream()
                .peek(message -> message.setSender(userDAO.getUser(message.getSender().getId())))
                .toList();
    }

    @Override
    public void createMessage(Message message) {
        messageDAO.createMessage(message);
    }
}
