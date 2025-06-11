package in.tech_camp.protospace.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import in.tech_camp.protospace.component.ImageUrl;
import in.tech_camp.protospace.entity.ProtoEntity;
import in.tech_camp.protospace.entity.UserEntity;
import in.tech_camp.protospace.form.CommentForm; 
import in.tech_camp.protospace.form.ProtoForm;
import in.tech_camp.protospace.repository.ProtoRepository;
import jakarta.validation.Valid;

@Controller
public class ProtoController {

    private final ImageUrl imageUrl;
    private final ProtoRepository protoRepository;

    public ProtoController(ImageUrl imageUrl, ProtoRepository protoRepository) {
        this.imageUrl = imageUrl;
        this.protoRepository = protoRepository;
    }

    // トップ・新規投稿画面共通表示
    @GetMapping({"/", "/new"})
    public String showNewForm(Model model) {
        model.addAttribute("protoForm", new ProtoForm());
        return "protos/new";
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
        proto.setCatchCopy(protoForm.getCatchcopy());
        proto.setConcept(protoForm.getConcept());
        proto.setImage(fileName != null ? "/uploads/" + fileName : null);
        proto.setUserName("test_user"); // ログイン未実装の仮置き

        try {
            protoRepository.save(proto); // IDがセットされる
            return "redirect:/protos/" + proto.getId(); // 保存後に詳細ページへ遷移
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
        if (proto == null) {
            model.addAttribute("error", "指定された投稿が見つかりません。");
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
