package in.tech_camp.protospace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/users/login")
    public String showLoginForm() {
        return "users/login";  // users/login.html を表示
    }
}
