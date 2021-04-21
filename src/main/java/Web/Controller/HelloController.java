package Web.Controller;

import Hibernate.model.User;
import Hibernate.service.UserService;
import Hibernate.service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

    private UserService userService = new UserServiceImpl();

    {
        System.out.println("UserService link: " + userService);
    }

    @GetMapping(value = "/")
    public String helloMethod() {
        return "index";
    }

    @GetMapping(value = "/addUser")
    public String addUser(
            @RequestParam (value = "name", required = false) String name,
            @RequestParam (value = "lastName", required = false) String lastName,
            @RequestParam (value = "age", required = false) String age) {


        if (age != null) {
            userService.add(new User(name, lastName, Integer.parseInt(age)));
        } else {
            userService.add(new User(name, lastName, 0));
        }
        return "addUser";
    }

    @GetMapping(value = "/editUser")
    public String editUser() {
        return "editUser";
    }

    @GetMapping(value = "/usersView")
    public String usersView(@RequestParam (value = "id", required = false) String id,
                            Model model) {

        if (id != null) {
            model.addAttribute("User", userService.getUserByID(Integer.parseInt(id)));
        }
        return "usersView";
    }
}
