package project.service.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.Exceptions.DuplicateLoginException;
import project.Exceptions.UserNotFoundException;
import project.dao.Chat.ChatDAO;
import project.dao.User.UserDAO;
import project.models.Chat;
import project.models.User;

import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {
    UserDAO userDAO;
    ChatDAO chatDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, ChatDAO chatDAO) {
        this.userDAO = userDAO;
        this.chatDAO = chatDAO;
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    @Transactional
    public void createUser(User user) throws DuplicateLoginException {
        userDAO.createUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        for (Chat chat : chatDAO.getAllUsersChats(id)) {
            if (!chat.isGroupChat()) {
                chatDAO.deleteChat(chat.getId());
            }
        }
        userDAO.deleteUser(id);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    @Override
    @Transactional
    public User getUser(int id) throws UserNotFoundException {
        return userDAO.getUser(id);
    }

    @Override
    @Transactional
    public Optional<Integer> getUserId(String login, String password) {
        return userDAO.getUserId(login, password);
    }

    @Override
    @Transactional
    public List<User> getAllChatMembers(int chat_id) {
        return chatDAO.getAllChatMembers(chat_id).stream().map(id -> {
            try {
                return userDAO.getUser(id);
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }

    @Override
    @Transactional
    public List<User> getAllSimilarUsers(String searching_login) {
        return userDAO.getAllSimilarUsers(searching_login);
    }
}
