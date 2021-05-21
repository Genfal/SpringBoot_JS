package CRUD.controller;

import CRUD.model.Role;
import CRUD.model.User;
import CRUD.service.RoleService;
import CRUD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private UserService userService;

    private RoleService roleService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/addUser")
    public String addUser(
            @ModelAttribute("new_user") User user,
            @ModelAttribute("role") String role) {

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleService.getRoleByName("ROLE_USER"));
        if (!role.equals("ROLE_USER")) {
            roleSet.add(roleService.getRoleByName(role));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roleSet);
        userService.add(user);
        return "redirect:/admin/";
    }

    @PatchMapping(value = "/editUser")
    public String editUser(
            @ModelAttribute("ID") String ID,
            @ModelAttribute("name") String name,
            @ModelAttribute("lastName") String lastName,
            @ModelAttribute("age") String age,
            @ModelAttribute("email") String email,
            @ModelAttribute("password") String password,
            @ModelAttribute("role") String role) {

        User user = userService.getUserByID(Long.parseLong(ID));
        userService.userDeleteRoles(user.getID());
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(Integer.parseInt(age));
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleService.getRoleByName("ROLE_USER"));
        if (!role.equals("ROLE_USER")) {
            roleSet.add(roleService.getRoleByName(role));
        }
        user.setRoles(roleSet);
        userService.update(user);
        return "redirect:/admin/";
    }

    @DeleteMapping(value = "/removeUser/{id}")
    public String removeUser(@PathVariable(value = "id") long ID) {
        userService.remove(ID);
        return "redirect:/admin/";
    }

    @GetMapping(value = "/")
    public String usersView(Principal principal, Model model) {
        model.addAttribute("currentUser", userService.getUserByEmail(principal.getName()));
        model.addAttribute("users", userService.listUsers());
        model.addAttribute("roles", roleService.getRoles());
        model.addAttribute("new_user", new User());
        return "admin/admin";
    }
}
