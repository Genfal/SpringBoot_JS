package CRUD.dao;

import CRUD.model.User;

import java.util.List;

public interface UserDAO {
    void remove(long ID);
    void add(User user);
    void update(User user);
    List<User> listUsers();
    User getUserByID(long ID);
    User getUserByLogin(String login);
}
