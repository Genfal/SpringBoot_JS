package Hibernate.dao;

import Hibernate.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class UserDAOImpl implements UserDAO{

    @Autowired
    private SessionFactory sessionFactory;

    {
        System.out.println("SessionFactory link: " + sessionFactory);
    }

    @Override
    @Transactional(readOnly = false)
    public void remove(User user) {
        sessionFactory.getCurrentSession().remove(user);
    }

    @Override
    @Transactional(readOnly = false)
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public User getUserByID(int ID) {
        TypedQuery<User> userTypedQuery = sessionFactory
                .getCurrentSession()
                .createQuery("select u from User u WHERE u.ID = :ID", User.class);
        userTypedQuery.setParameter("ID", ID);
        return userTypedQuery.getSingleResult();
    }

    @Override
    public List<User> listUsers() {
        return sessionFactory
                .getCurrentSession()
                .createQuery("select u from User u", User.class)
                .getResultList();
    }
}
