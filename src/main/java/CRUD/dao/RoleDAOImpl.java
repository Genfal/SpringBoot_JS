package CRUD.dao;

import CRUD.model.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
@Transactional(readOnly = true)
public class RoleDAOImpl implements RoleDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Role role) {
        entityManager.persist(role);
    }

    @Override
    public Role getRoleByID(long ID) {
        TypedQuery<Role> roleTypedQuery = entityManager
                .createQuery("select r from Role r where r.ID = :ID", Role.class);
        roleTypedQuery.setParameter("ID", ID);
        return roleTypedQuery.getSingleResult();
    }

    @Override
    public Role getRoleByName(String roleName) {
        TypedQuery<Role> roleTypedQuery = entityManager
                .createQuery("select r from Role r where r.role = :role", Role.class);
        roleTypedQuery.setParameter("role", roleName);
        return roleTypedQuery.getSingleResult();
    }
}
