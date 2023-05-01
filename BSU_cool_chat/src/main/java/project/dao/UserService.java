package project.dao;

import project.models.User;

import java.util.Optional;

public interface UserService {

    Iterable<User> getAllUsers();
    void createUser(User user);
    void deleteUser(int id);
    void updateUser(User user);
    User getById(int id);
    Optional<Integer> getUserId(String login, String password);
}
