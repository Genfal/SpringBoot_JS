package CRUD.dao;

import CRUD.model.Role;
import CRUD.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserDAOImpl implements UserDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void remove(long ID) {
        User user = entityManager.find(User.class, ID);
        entityManager.remove(user);
    }

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public User getUserByID(long ID) {
        TypedQuery<User> userTypedQuery = entityManager
                .createQuery("select u from User u WHERE u.ID = :ID", User.class);
        userTypedQuery.setParameter("ID", ID);
        return userTypedQuery.getSingleResult();
    }

    @Override
    public User getUserByLogin(String login) {
        TypedQuery<User> userTypedQuery = entityManager
                .createQuery("select u from User u where u.login = :login", User.class);
        userTypedQuery.setParameter("login", login);
        return userTypedQuery.getSingleResult();
    }

    @Override
    public List<User> listUsers() {
        return entityManager
                .createQuery("select u from User u", User.class)
                .getResultList();
    }
}
