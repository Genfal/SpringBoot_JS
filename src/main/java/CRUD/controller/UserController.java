package CRUD.controller;

import CRUD.model.Role;
import CRUD.model.User;
import CRUD.service.RoleService;
import CRUD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.Transient;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {

    private PasswordEncoder passwordEncoder;

    private UserService userService;

    private RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/user")
    public String showUser(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));
        return "user/user";
    }

//
//    Добавить адинистратора
//
//    @GetMapping(value = "/newUser")
//    public String newUser() {
//        Set<Role> roleSet = new HashSet<>();
//        roleSet.add(roleService.getRoleByName("ROLE_USER"));
//        roleSet.add(roleService.getRoleByName("ROLE_ADMIN"));
//        User user = new User("admin", "admin", 0, "admin", passwordEncoder.encode("admin"), roleSet);
//        userService.add(user);
//        return "redirect:/";
//    }
}
