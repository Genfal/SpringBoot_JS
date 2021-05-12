package CRUD.controller;

import CRUD.controller.utils.RoleCheck;
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
            @ModelAttribute("roles[]") RoleCheck roles) {

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleService.getRoleByName("ROLE_USER"));
        for (String roleName : roles.getRoles()) {
            roleSet.add(roleService.getRoleByName(roleName));
        }
        user.setRoles(roleSet);
        userService.add(user);
        return "redirect:/admin/";
    }

    @PatchMapping(value = "/editUser")
    public String editUser(
            @ModelAttribute("user") User user,
            @ModelAttribute("roles[]") RoleCheck roles) {

        userService.userDeleteRoles(user.getID());
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleService.getRoleByName("ROLE_USER"));
        for (String roleName : roles.getRoles()) {
            roleSet.add(roleService.getRoleByName(roleName));
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
    public String usersView(Model model) {
        model.addAttribute("users", userService.listUsers());
        return "usersView";
    }

    @GetMapping(value = "/addUserForm")
    public String addUserForm(Model model) {
        model.addAttribute("roles", roleService.getRoles());
        model.addAttribute("user", new User());
        return "addUserForm";
    }

    @GetMapping(value = "/editUserForm/{id}")
    public String editUserForm(
            @PathVariable("id") long ID,
            Model model) {

        model.addAttribute("roles", roleService.getRoles());
        model.addAttribute("user", userService.getUserByID(ID));
        return "editUserForm";
    }
}
