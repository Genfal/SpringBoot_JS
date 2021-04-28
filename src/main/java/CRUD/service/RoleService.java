package CRUD.service;

import CRUD.model.Role;

public interface RoleService {
    void save(Role role);
    Role getRoleByID(long ID);
    Role getRoleByName(String roleName);
}
