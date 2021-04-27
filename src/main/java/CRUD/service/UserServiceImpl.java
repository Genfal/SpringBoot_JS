package CRUD.service;

import CRUD.dao.UserDAO;
import CRUD.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDAO userDAO;

    @Override
    public void remove(long ID) {
        userDAO.remove(ID);
    }

    @Override
    public void add(User user) {
        userDAO.add(user);
    }

    @Override
    public void update(User user) {
        userDAO.update(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByID(long ID) {
        return userDAO.getUserByID(ID);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByLogin(String login) {
        return userDAO.getUserByLogin(login);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> listUsers() {
        return userDAO.listUsers();
    }
}
