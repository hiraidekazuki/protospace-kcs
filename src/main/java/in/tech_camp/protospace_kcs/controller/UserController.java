package in.tech_camp.protospace_kcs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.tech_camp.protospace_kcs.entity.UserEntity;
import in.tech_camp.protospace_kcs.repository.UserRepository;

@Controller
public class UserController {
   private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


// 登録フォーム表示
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("user", new UserEntity(null, "", "", "", "", "", ""));
        return "users/new"; // → templates/users/new.html
    }

     // 登録処理
    @PostMapping("/users")
    public String create(@ModelAttribute UserEntity user) {
        userRepository.insert(user);
        return "redirect:/users"; // 一覧表示などにリダイレクト 6/10 一覧表示へ飛ぶように編集を行う。
    }
}
  