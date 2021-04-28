package CRUD.controller;

import CRUD.model.Role;
import CRUD.model.User;
import CRUD.service.RoleService;
import CRUD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PostMapping(value = "/addUser")
    public String addUser(
            @ModelAttribute("user") User user,
            @RequestParam(value = "admin", defaultValue = "false") boolean admin) {

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleByName("ROLE_USER"));
        if (admin) {
            roles.add(roleService.getRoleByName("ROLE_ADMIN"));
        }
        user.setRoles(roles);
        userService.add(user);
        return "redirect:/admin/";
    }

    @DeleteMapping(value = "/removeUser/{id}")
    public String removeUser(@PathVariable(value = "id") long ID) {
        userService.remove(ID);
        return "redirect:/admin/";
    }

    @PatchMapping(value = "/editUser")
    public String editUser(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin/";
    }

    @GetMapping(value = "/")
    public String usersView(Model model) {
        model.addAttribute("users", userService.listUsers());
        return "usersView";
    }

    @GetMapping(value = "/addUserForm")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        return "addUserForm";
    }

    @GetMapping(value = "/editUserForm/{id}")
    public String editUserForm(
            @PathVariable("id") long ID,
            Model model) {

        model.addAttribute("user", userService.getUserByID(ID));
        return "editUserForm";
    }
}
