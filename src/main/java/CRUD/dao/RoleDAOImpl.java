package CRUD.dao;

import CRUD.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.Set;

@Repository
public class RoleDAOImpl implements RoleDAO{

    @PersistenceContext
    private EntityManager entityManager;

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

    @Override
    public Set<Role> getRoles() {
        TypedQuery<Role> roleTypedQuery = entityManager
                .createQuery("select r from Role r where not r.role = 'ROLE_USER'", Role.class);
        return new HashSet<>(roleTypedQuery.getResultList());
    }
}
