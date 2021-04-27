package CRUD.dao;

import CRUD.model.Role;

public interface RoleDAO {
    void save(Role role);
    Role getRoleByID(long ID);
    Role getRoleByName(String roleName);
}
