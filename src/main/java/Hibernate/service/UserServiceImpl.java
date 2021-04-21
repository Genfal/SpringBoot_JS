package Hibernate.service;

import Hibernate.dao.UserDAOImpl;
import Hibernate.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private UserDAOImpl userDAO = new UserDAOImpl();

    {
        System.out.println("UserDAOImpl link: " + userDAO);
    }

    @Override
    public void remove(User user) {
        userDAO.remove(user);
    }

    @Override
    public void add(User user) {
        userDAO.add(user);
    }

    @Override
    public User getUserByID(int ID) {
        return userDAO.getUserByID(ID);
    }

    @Override
    public List<User> listUsers() {
        return userDAO.listUsers();
    }
}
