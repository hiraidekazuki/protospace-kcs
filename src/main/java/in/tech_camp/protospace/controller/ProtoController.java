package in.tech_camp.protospace.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import in.tech_camp.protospace.component.ImageUrl;
import in.tech_camp.protospace.custom_user.CustomUserDetails;
import in.tech_camp.protospace.entity.ProtoEntity;
import in.tech_camp.protospace.form.ProtoForm;
import in.tech_camp.protospace.repository.ProtoRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class ProtoController {

    private final ProtoRepository protoRepository;
    private final ImageUrl imageUrl;

    // アップロード画像の保存先（デフォルト）
    @Value("${upload.image.dir:/tmp/myapp/uploads/images}")
    private String defaultUploadDir;

    // コンストラクタインジェクション
    @Autowired
    public ProtoController(ProtoRepository protoRepository, ImageUrl imageUrl) {
        this.protoRepository = protoRepository;
        this.imageUrl = imageUrl;
    }

    // トップページ表示処理
    @GetMapping("/")
    public String showIndex(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<ProtoEntity> prototypes = protoRepository.findAll(); // すべてのプロトタイプを取得
        model.addAttribute("prototypes", prototypes); // Viewにデータ渡す
        model.addAttribute("user", userDetails != null ? userDetails.getUserEntity() : null); // ログインユーザー渡す
        return "protos/index"; // indexテンプレートを表示
    }

    // 新規作成フォームを表示
    @GetMapping("/protos/new")
    public String newProto(Model model) {
        model.addAttribute("protoForm", new ProtoForm()); // 空のフォームを渡す
        return "protos/new"; // newテンプレートを表示
    }

    // プロトタイプ新規作成処理
    @PostMapping("/protos")
    public String createProto(
        @Valid @ModelAttribute("protoForm") ProtoForm protoForm,
        BindingResult bindingResult,
        @AuthenticationPrincipal CustomUserDetails userDetails,
        Model model
    ) {
        // バリデーションエラーがある場合、フォーム再表示
        if (bindingResult.hasErrors()) {
            return "protos/new";
        }

        // フォームの値をエンティティに設定
        ProtoEntity entity = new ProtoEntity();
        entity.setName(protoForm.getName());
        entity.setCatchCopy(protoForm.getCatchCopy());
        entity.setConcept(protoForm.getConcept());

        // ユーザー情報を設定（ログインしている場合のみ）
        if (userDetails != null) {
            entity.setUser(userDetails.getUserEntity());
            entity.setUserId(userDetails.getUserEntity().getId());
            entity.setUserName(userDetails.getUsername());
        } else {
            entity.setUserName("test_user"); // 未ログイン時（テスト用）
        }

        // 画像ファイルがある場合、保存してパスを設定
        MultipartFile imageFile = protoForm.getImage();
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                                 + "_" + imageFile.getOriginalFilename();

                String uploadDir = imageUrl != null ? imageUrl.getUrl() : defaultUploadDir;
                Path imagePath = Paths.get(uploadDir, fileName);
                Files.createDirectories(imagePath.getParent());
                Files.copy(imageFile.getInputStream(), imagePath);

                entity.setImage("/uploads/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("error", "画像のアップロードに失敗しました。");
                return "protos/new";
            }
        }

        // DBに保存
        try {
            protoRepository.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "保存に失敗しました。");
            return "protos/new";
        }

        return "redirect:/"; // 保存後はトップにリダイレクト
    }

    // 詳細ページの表示
    @GetMapping("/detail/{id}")
    public String showDetail(
        @PathVariable Long id,
        Model model,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        ProtoEntity proto = protoRepository.findById(id); // IDから取得
        if (proto == null) {
            return "redirect:/error/404";
        }

        model.addAttribute("proto", proto);
        model.addAttribute("user", userDetails != null ? userDetails.getUserEntity() : null);
        return "protos/detail";
    }

    // 編集ページの表示
    @GetMapping("/protos/{id}/edit")
    public String editProto(
        @PathVariable Long id,
        Model model,
        @AuthenticationPrincipal CustomUserDetails userDetails,
        HttpServletRequest request
    ) {
        ProtoEntity proto = protoRepository.findById(id);
        if (proto == null) {
            return "redirect:/error/404";
        }

        // 投稿者本人以外はアクセス禁止（トップに戻す）
        if (userDetails == null || !proto.getUserId().equals(userDetails.getUserEntity().getId())) {
            return "redirect:/";
        }

        // 既存データをフォームに詰め替える
        ProtoForm protoForm = new ProtoForm();
        protoForm.setName(proto.getName());
        protoForm.setCatchCopy(proto.getCatchCopy());
        protoForm.setConcept(proto.getConcept());

        model.addAttribute("protoForm", protoForm);
        model.addAttribute("protoId", id);
        model.addAttribute("proto", proto);

        // CSRFトークンを明示的に渡す（Thymeleaf向け）
        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("_csrf", csrfToken);

        return "protos/edit";
    }

    // プロトタイプ更新処理
    @PostMapping("/protos/{id}")
    public String updateProto(
        @PathVariable Long id,
        @Valid @ModelAttribute("protoForm") ProtoForm protoForm,
        BindingResult bindingResult,
        @AuthenticationPrincipal CustomUserDetails userDetails,
        Model model
    ) {
        ProtoEntity proto = protoRepository.findById(id);
        if (proto == null) {
            return "redirect:/error/404";
        }

        // 投稿者本人かどうかチェック
        if (userDetails == null || !proto.getUserId().equals(userDetails.getUserEntity().getId())) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("protoId", id);
            return "protos/edit";
        }

        // フォームの内容でエンティティを更新
        proto.setName(protoForm.getName());
        proto.setCatchCopy(protoForm.getCatchCopy());
        proto.setConcept(protoForm.getConcept());

        // 新しい画像がある場合のみ保存し直す
        MultipartFile imageFile = protoForm.getImage();
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                                 + "_" + imageFile.getOriginalFilename();

                String uploadDir = imageUrl != null ? imageUrl.getUrl() : defaultUploadDir;
                Path imagePath = Paths.get(uploadDir, fileName);
                Files.createDirectories(imagePath.getParent());
                Files.copy(imageFile.getInputStream(), imagePath);

                proto.setImage("/uploads/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("error", "画像のアップロードに失敗しました。");
                model.addAttribute("protoId", id);
                return "protos/edit";
            }
        }

        // DB更新
        try {
            protoRepository.update(proto);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "更新に失敗しました。");
            model.addAttribute("protoId", id);
            return "protos/edit";
        }

        return "redirect:/detail/" + id;
    }

    // ★ プロトタイプ削除処理（追加機能）
    @PostMapping("/protos/{id}/delete")
    public String deleteProto(
        @PathVariable Long id,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        ProtoEntity proto = protoRepository.findById(id);
        if (proto == null) {
            return "redirect:/error/404";
        }

        // 投稿者本人以外は削除禁止
        if (userDetails == null || !proto.getUserId().equals(userDetails.getUserEntity().getId())) {
            return "redirect:/";
        }

        protoRepository.deleteById(id); // 削除
        return "redirect:/"; // トップに戻る
    }
}
