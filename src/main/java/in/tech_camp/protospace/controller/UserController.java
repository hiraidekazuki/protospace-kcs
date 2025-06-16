package in.tech_camp.protospace.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import in.tech_camp.protospace.custom_user.CustomUserDetails;
import in.tech_camp.protospace.entity.ProtoEntity;
import in.tech_camp.protospace.entity.UserEntity;
import in.tech_camp.protospace.repository.ProtoRepository;
import in.tech_camp.protospace.repository.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final ProtoRepository protoRepository;

    public UserController(UserRepository userRepository, ProtoRepository protoRepository) {
        this.userRepository = userRepository;
        this.protoRepository = protoRepository;
    }

    // /users/login にアクセスしたとき login.html を表示
    @GetMapping("/login")
    public String showLoginForm() {
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
