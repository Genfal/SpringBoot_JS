package Hibernate.service;

import Hibernate.model.User;

import java.util.List;

public interface UserService {
    void remove(User user);
    void add(User user);
    List<User> listUsers();
    User getUserByID(int ID);
}