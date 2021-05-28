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

    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

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
    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> listUsers() {
        return userDAO.listUsers();
    }
}
