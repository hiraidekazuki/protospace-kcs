package in.tech_camp.protospace.controller;

import java.util.List;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import in.tech_camp.protospace.component.ImageUrl;
import in.tech_camp.protospace.custom_user.CustomUserDetail;
import in.tech_camp.protospace.entity.ProtoEntity;

import in.tech_camp.protospace.repository.ProtoRepository;

@Controller
public class ProtoController {

    private final CommentRepository commentRepository;
    private final ImageUrl imageUrl;
    private final ProtoRepository protoRepository;

    @Autowired
    public ProtoController(ImageUrl imageUrl, ProtoRepository protoRepository) {
        this.imageUrl = imageUrl;
        this.protoRepository = protoRepository;
        this.commentRepository = commentRepository;
    }

     @GetMapping("/")
    public String showIndex(Model model) {
        List<ProtoEntity> prototypes = protoRepository.findAll();
        model.addAttribute("prototypes", prototypes);

        return "protos/index";
    }

    // 投稿作成処理

    @PostMapping("/protos")
    public String createProto(
        @Valid @ModelAttribute("protoForm") ProtoForm protoForm,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "protos/new";
        }

        String fileName = null;
        MultipartFile imageFile = protoForm.getImage();

        // 画像保存処理
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String uploadDir = imageUrl.getUrl();
                fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                         + "_" + imageFile.getOriginalFilename();
                Path imagePath = Paths.get(uploadDir, fileName);
                Files.createDirectories(imagePath.getParent());
                Files.copy(imageFile.getInputStream(), imagePath);
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("error", "画像の保存に失敗しました。");
                return "protos/new";
            }
        }

        // フォームから受け取った値でProtoEntityを構築
        ProtoEntity proto = new ProtoEntity();
        proto.setName(protoForm.getName());
        proto.setCatchCopy(protoForm.getCatchCopy());
        proto.setConcept(protoForm.getConcept());
        proto.setImage(fileName != null ? "/uploads/" + fileName : null);

        // ログインユーザーIDをセット
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetail) {
            CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
            proto.setUserId(userDetails.getId());
        } else {
            proto.setUserId(0); // ログインしていない場合の仮置き
        }

        System.out.println("=== デバッグログ ===");
        System.out.println("フォームのname: " + protoForm.getName());
        System.out.println("エンティティのname: " + proto.getName());
        System.out.println("セットされたユーザーID: " + proto.getUserId());

        try {
            protoRepository.save(proto); // IDがセットされる
            return "redirect:/protos"; // 保存後に一覧ページへ遷移
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "保存に失敗しました。");
            return "protos/new";
        }
    }

    // 投稿詳細ページ表示
    @GetMapping("/protos/{protoId}")
    public String showProtoDetail(@PathVariable("protoId") Integer protoId, Model model) {
        ProtoEntity proto = protoRepository.findById(protoId);
        CommentForm commentForm = new CommentForm();
        if (proto == null) {
            model.addAttribute("error", "指定された投稿が見つかりません。");
            model.addAttribute("commentForm", commentForm);
            return "error"; // 適切なエラーページに切り替えてください
        }

        // コメントフォームオブジェクトをモデルに追加
        model.addAttribute("comment", new CommentForm());

        // 仮ユーザー情報セット（nullチェック）
        if (proto.getUser() == null) {
            UserEntity dummyUser = new UserEntity();
            dummyUser.setId(0);             // 仮ID
            dummyUser.setName("仮ユーザー");  // 仮ユーザー名
            proto.setUser(dummyUser);
        }

        if (proto.getComments() == null){
            proto.setComments(new ArrayList<>());
        }

        model.addAttribute("proto", proto);
        return "protos/detail";
    }
}

// // テスト用詳細表示（手動作成のダミーデータ）
// @GetMapping("/test-detail")
// public String testDetail(Model model) {
//     ProtoEntity proto = new ProtoEntity();
//     proto.setId(1);
//     proto.setName("テスト");
//     proto.setCatchCopy("これはキャッチコピーです！");
//     proto.setConcept("これはコンセプトです。");
//     proto.setImage("/uploads/test_image.png");
//     proto.setUserName("test_user");

//     proto.setUser(new UserEntity()); // null回避用の空ユーザー

//     model.addAttribute("proto", proto);
//     return "protos/detail";
// }

    // @PostMapping("/protos")
    // public String createProto(
    //     @Valid @ModelAttribute("protoForm") ProtoForm protoForm,
    //     BindingResult bindingResult,
    //     Model model,
    //     @AuthenticationPrincipal CustomUserDetails userDetails
    // ) {
    //     if (bindingResult.hasErrors()) {
    //         return "protos/new";
    //     }

    //     ProtoEntity proto = new ProtoEntity();
    //     proto.setName(protoForm.getName());
    //     proto.setCatchcopy(protoForm.getCatchcopy());
    //     proto.setConcept(protoForm.getConcept());
    //     // 画像アップロード未実装のため、protoFormから直接取得
    //     // proto.setImage(protoForm.getImage());
    //     // proto.setUser_name(userDetails != null ? userDetails.getUsername() : "anonymous");

    //     try {
    //         protoRepository.insert(proto);
    //     } catch (Exception e) {
    //         System.out.println("エラー：" + e);
    //         return "redirect:/protos/new";
    //     }

    //     return "redirect:/";
    // }
}

