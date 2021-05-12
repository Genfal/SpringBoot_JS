package CRUD.service;

import CRUD.model.Role;

import java.util.Set;

public interface RoleService {
    void save(Role role);
    Role getRoleByID(long ID);
    Role getRoleByName(String roleName);
    Set<Role> getRoles();
}
