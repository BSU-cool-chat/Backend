package project.service.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.Exceptions.DuplicateLoginException;
import project.Exceptions.UserNotFoundException;
import project.dao.Chat.ChatDAO;
import project.dao.User.UserDAO;
import project.models.User;

import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {
    UserDAO userDAO;
    ChatDAO chatDAO;

    @Autowired
    UserServiceImpl(UserDAO userDAO, ChatDAO chatDAO) {
        this.userDAO = userDAO;
        this.chatDAO = chatDAO;
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public void createUser(User user) throws DuplicateLoginException {
        userDAO.createUser(user);
    }

    @Override
    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }

    @Override
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    @Override
    public User getUser(int id) throws UserNotFoundException {
        return userDAO.getUser(id);
    }

    @Override
    public Optional<Integer> getUserId(String login, String password) {
        return userDAO.getUserId(login, password);
    }

    @Override
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
    public List<User> getAllSimilarUsers(String searching_login) {
        return userDAO.getAllSimilarUsers(searching_login);
    }
}
