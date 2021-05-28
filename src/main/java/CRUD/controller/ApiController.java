package CRUD.controller;

import CRUD.model.Role;
import CRUD.model.User;
import CRUD.service.RoleService;
import CRUD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/api")
public class ApiController {

    private UserService userService;

    private RoleService roleService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public ApiController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/")
    @ResponseBody
    public User addUser(@RequestBody User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.add(user);
        return userService.getUserByID(user.getID());
    }

    @PatchMapping(value = "/{id}")
    @ResponseBody
    public User editUser(@RequestBody User editedUser,
                         @PathVariable("id") long ID) {

        User user = userService.getUserByID(ID);
        user.setName(editedUser.getName());
        user.setLastName(editedUser.getLastName());
        user.setAge(editedUser.getAge());
        user.setEmail(editedUser.getEmail());
        user.setPassword(passwordEncoder.encode(editedUser.getPassword()));
        user.setRoles(editedUser.getRoles());
        userService.update(user);
        return user;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUser(@PathVariable(value = "id") long ID) {
        userService.remove(ID);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public User getUser(@PathVariable(value = "id") long ID) {
        return userService.getUserByID(ID);
    }

    @GetMapping(value = "/")
    @ResponseBody
    public List<User> getAllUsers() {
        return userService.listUsers();
    }

    @GetMapping(value = "/getCurrentUser")
    @ResponseBody
    public User getCurrentUser(Principal principal) {
        return userService.getUserByEmail(principal.getName());
    }

    @GetMapping(value = "/getRoles")
    @ResponseBody
    public Set<Role> getRoles() {
        return roleService.getRoles();
    }

    @GetMapping(value = "/getRoleByRoleName/{roleName}")
    @ResponseBody
    public Set<Role> getRoleByName(@PathVariable(value = "roleName") String roleName) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleByName("ROLE_USER"));
        roles.add(roleService.getRoleByName(roleName));
        return roles;
    }

//    @GetMapping(value = "/newUser")
//    public void createUser() {
//        Set<Role> roleSet = new HashSet<>();
//        roleSet.add(roleService.getRoleByName("ROLE_USER"));
//        User user = new User("Test", "Test", 0, "Test", "Test", roleSet);
//        user.setPassword("Test");
//        userService.add(user);
//    }
}
