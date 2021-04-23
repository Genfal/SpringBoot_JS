package CRUD.controller;

import CRUD.model.User;
import CRUD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

//    @GetMapping(value = "/")
//    public String helloMethod() {
//        return "index";
//    }

    @GetMapping(value = "/addUser")
    public String addUser(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "age", required = false) Integer age,
            Model model) {

        userService.add(new User(name, lastName, age));
        model.addAttribute("users", userService.listUsers());
        return "redirect:/";
    }

    @GetMapping(value = "/removeUser")
    public String removeUser(
            @RequestParam(value = "id") long ID,
            Model model) {

        userService.remove(ID);
        model.addAttribute("users", userService.listUsers());
        return "redirect:/";
    }

    @GetMapping(value = "/editUser")
    public String editUser(
            @RequestParam(value = "id") long ID,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "age",required = false) Integer age,
            Model model) {

        User user = new User();
        user.setID(ID);
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);
        userService.update(user);
        model.addAttribute("users", userService.listUsers());
        return "redirect:/";
    }

    @GetMapping(value = "/")
    public String usersView(Model model) {
        model.addAttribute("users", userService.listUsers());
        return "usersView";
    }

    @GetMapping(value = "/addUserForm")
    public String addUserForm() {
        return "addUserForm";
    }

    @GetMapping(value = "/editUserForm")
    public String editUserForm(
            @RequestParam(value = "id", required = false) long ID,
            Model model) {

        model.addAttribute("user", userService.getUserByID(ID));
        return "editUserForm";
    }
}
