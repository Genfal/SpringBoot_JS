package CRUD.controller;

import CRUD.model.Role;
import CRUD.model.User;
import CRUD.service.RoleService;
import CRUD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/registrationForm")
    public String registrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registrationForm";
    }

    @GetMapping(value = "/registrationFormError")
    public String registrationFormError(Model model) {
        model.addAttribute("user", new User());
        return "registrationForm";
    }

    @PostMapping(value = "/registration")
    public String registration(
            @ModelAttribute("user") User user,
            @RequestParam(value = "admin", defaultValue = "false") boolean admin) {

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleByName("ROLE_USER"));
        if (admin) {
            roles.add(roleService.getRoleByName("ROLE_ADMIN"));
        }
        user.setRoles(roles);
        userService.add(user);
        return "redirect:/login";
    }

    @GetMapping(value = "/logout")
    public String logout(Principal principal) {
        return "redirect:/login";
    }

    @GetMapping(value = "/user")
    public String showUser(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByLogin(principal.getName()));
        return "user";
    }
}
