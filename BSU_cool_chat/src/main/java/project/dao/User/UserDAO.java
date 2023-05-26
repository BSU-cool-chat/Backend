package project.dao.User;

import project.Exceptions.DuplicateLoginException;
import project.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    List<User> getAllUsers();

    void createUser(User user) throws DuplicateLoginException;

    void deleteUser(int id);

    void updateUser(User user);

    User getUser(int id);

    Optional<Integer> getUserId(String login, String password);
    List<User> getAllSimilarUsers(String searching_login);
}
