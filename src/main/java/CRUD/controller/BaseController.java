package CRUD.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/logout")
    public String logout() {
        return "redirect:/login";
    }

    @GetMapping(value = "/")
    public String startPage() {
        return "mainPage";
    }
}
