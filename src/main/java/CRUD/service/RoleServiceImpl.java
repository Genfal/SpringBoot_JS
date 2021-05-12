package CRUD.service;

import CRUD.dao.RoleDAO;
import CRUD.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDAO roleDAO;

    @Override
    @Transactional(readOnly = false)
    public void save(Role role) {
        roleDAO.save(role);
    }

    @Override
    public Role getRoleByID(long ID) {
        return roleDAO.getRoleByID(ID);
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleDAO.getRoleByName(roleName);
    }

    @Override
    public Set<Role> getRoles() {
        return roleDAO.getRoles();
    }
}
