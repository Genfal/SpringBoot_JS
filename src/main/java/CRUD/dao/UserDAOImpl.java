package CRUD.dao;

import CRUD.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

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
    @Transactional
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
    public List<User> listUsers() {
        return entityManager
                .createQuery("select u from User u", User.class)
                .getResultList();
    }
}
