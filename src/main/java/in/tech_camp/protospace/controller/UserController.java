package in.tech_camp.protospace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    // /login にアクセスしたとき login.html を表示
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";  // templates/login.html を表示
    }
}