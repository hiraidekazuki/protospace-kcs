package in.tech_camp.protospace_kcs.controller;

import java.security.Principal;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.tech_camp.protospace_kcs.entity.UserEntity;
import in.tech_camp.protospace_kcs.form.LoginForm;
import in.tech_camp.protospace_kcs.form.UserForm;
import in.tech_camp.protospace_kcs.repository.UserRepository;
import jakarta.validation.Valid;

@Controller
public class UserController {
   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/new")
     public String showForm(Model model) {
     model.addAttribute("userForm", new UserForm());
      return "users/new";
    }


     // 登録処理
    @PostMapping("/users")
    public String create(@ModelAttribute @Valid UserForm form, 
                         BindingResult result,
                         Model model ){
                
                 // パスワード確認チェック
        if (!form.getPassword().equals(form.getPasswordConfirmation())) {
            result.rejectValue("passwordConfirmation", "error.passwordConfirmation", "パスワードが一致しません");
        }

        // メール重複チェック（userRepositoryにexistsByEmailメソッドが必要）
        if (userRepository.existsByEmail(form.getEmail())) {
            result.rejectValue("email", "error.email", "このメールアドレスはすでに登録されています");
        }

        if (result.hasErrors()) {
            return "users/new";  // 入力フォームに戻る
        }

        // UserForm -> UserEntity変換（パスワードはハッシュ化）
        UserEntity user = new UserEntity();
        user.setEmail(form.getEmail());
        user.setName(form.getName());
        user.setProfile(form.getProfile());
        user.setGroupName(form.getGroupName()); // ここはcamelCaseに直すこと推奨
        user.setPost(form.getPost());

        // パスワードはハッシュ化 (例: BCryptなど)

        String hashedPassword = passwordEncoder.encode((CharSequence) form.getPassword());

        user.setPassword(hashedPassword);

        // データベースに保存
        userRepository.insert(user);
        return "redirect:/users"; // 一覧表示などにリダイレクト 6/10 一覧表示へ飛ぶように編集を行う。
    }

    //ログイン画面の表示
  @GetMapping("/users/login")
   public String loginForm(@RequestParam(value = "error", required = false) String error, Model model) {
    model.addAttribute("loginForm", new LoginForm());
    if (error != null) {
        model.addAttribute("loginError", "メールアドレスかパスワードが間違っています。");
    }
    return "users/login";
}

  @GetMapping("/users/mypage")
public String mypage(Model model, Principal principal) {
    String email = principal.getName(); // ログインユーザーのメールを取得
    model.addAttribute("email", email);
    return "users/mypage";
}
   
}
  