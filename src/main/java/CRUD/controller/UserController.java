package CRUD.controller;

import CRUD.model.User;
import CRUD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/addUser")
    public String addUser(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/";
    }

    @DeleteMapping(value = "/removeUser/{id}")
    public String removeUser(@PathVariable(value = "id") long ID) {
        userService.remove(ID);
        return "redirect:/";
    }

    @PatchMapping(value = "/editUser")
    public String editUser(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/";
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
