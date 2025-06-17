package in.tech_camp.protospace.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.tech_camp.protospace.custom_user.CustomUserDetails;
import in.tech_camp.protospace.entity.ProtoEntity;
import in.tech_camp.protospace.entity.UserEntity;
import in.tech_camp.protospace.form.LoginForm;
import in.tech_camp.protospace.form.UserForm;
import in.tech_camp.protospace.repository.ProtoRepository;
import in.tech_camp.protospace.repository.UserRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
   private final UserRepository userRepository;
   private final ProtoRepository protoRepository;
   private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository,ProtoRepository protoRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.protoRepository = protoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // サインアップ画面表示
    @GetMapping("/sign_up")
    public String showForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "users/new";
    }

    // 登録処理
    @PostMapping("")
    public String create(@ModelAttribute @Valid UserForm form, 
                         BindingResult result,
                         Model model) {
                
        // パスワード確認チェック
        if (!form.getPassword().equals(form.getPasswordConfirmation())) {
            result.rejectValue("passwordConfirmation", "error.passwordConfirmation", "パスワードが一致しません");
        }

        // メール重複チェック
        if (userRepository.existsByEmail(form.getEmail())) {
            result.rejectValue("email", "error.email", "このメールアドレスはすでに登録されています");
        }

        // バリデーションエラーがあればフォームに戻す
        if (result.hasErrors()) {
            return "users/new";  // 入力フォームに戻る
        }

        // UserForm -> UserEntity 変換
        UserEntity user = new UserEntity();
        user.setEmail(form.getEmail());
        user.setName(form.getName());
        user.setProfile(form.getProfile());
        user.setGroupName(form.getGroupName());
        user.setPost(form.getPost());

        // パスワードはハッシュ化
        String hashedPassword = passwordEncoder.encode(form.getPassword());
        user.setPassword(hashedPassword);

        // データベースに保存
        userRepository.insert(user);  // saveメソッドに修正

        return "redirect:/"; // 一覧表示などにリダイレクト
    }

    // ログイン画面表示
    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        model.addAttribute("loginForm", new LoginForm());
        if (error != null) {
            model.addAttribute("loginError", "メールアドレスかパスワードが間違っています。");
        }
        return "users/login";
    }

    // /users/mypage にアクセスしたときログインユーザーのmypageを表示（未ログインはnull）
    @GetMapping("/mypage")
    public String showMyPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        if (userDetails != null) {
            model.addAttribute("userEntity", userDetails.getUserEntity());
        } else {
            model.addAttribute("userEntity", null);
        }
        return "users/mypage";
    }

    // /users/{id} にアクセスしたとき指定ユーザー情報とそのプロトタイプ一覧を表示
    @GetMapping("/{id}")
    public String showUserPage(@PathVariable Long id, Model model) {
        UserEntity userEntity = userRepository.findById(id);
        if (userEntity == null) {
            return "redirect:/";
        }

        List<ProtoEntity> prototypes = protoRepository.findByUserId(userEntity.getId());

        model.addAttribute("userEntity", userEntity);
        model.addAttribute("prototypes", prototypes);

        return "users/mypage";
    }
}