package CRUD.dao;

import CRUD.model.Role;

import java.util.Set;

public interface RoleDAO {
    void save(Role role);
    Role getRoleByID(long ID);
    Role getRoleByName(String roleName);
    Set<Role> getRoles();
}
