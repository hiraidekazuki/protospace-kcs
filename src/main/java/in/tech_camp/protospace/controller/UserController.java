package in.tech_camp.protospace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    // ログインページのURLを /users/login に変更
    @GetMapping("/users/login")
    public String showLoginForm() {
        return "login";  // templates/login.html を表示
    }
}
