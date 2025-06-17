package in.tech_camp.protospace.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import in.tech_camp.protospace.form.CommentForm;
import in.tech_camp.protospace.form.ProtoForm;
import in.tech_camp.protospace.repository.ProtoRepository;
import jakarta.validation.Valid;

@Controller
public class ProtoController {

    private final ProtoRepository protoRepository;
    private final ImageUrl imageUrl;

    @Autowired
    public ProtoController(ProtoRepository protoRepository, ImageUrl imageUrl) {
        this.protoRepository = protoRepository;
        this.imageUrl = imageUrl;
    }

    // トップページ表示
    @GetMapping("/")
    public String showIndex(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<ProtoEntity> prototypes = protoRepository.findAll();
        model.addAttribute("prototypes", prototypes);
        model.addAttribute("user", userDetails != null ? userDetails.getUserEntity() : null);
        return "protos/index";
    }

    // 新規投稿フォーム表示
    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("protoForm", new ProtoForm());
        return "protos/new";
    }

    // 投稿作成処理
    @PostMapping("/protos")
    public String createProto(
        @Valid @ModelAttribute("protoForm") ProtoForm protoForm,
        BindingResult bindingResult,
        @AuthenticationPrincipal CustomUserDetails userDetails,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "protos/new";
        }

        String fileName = null;
        MultipartFile imageFile = protoForm.getImage();

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

        ProtoEntity proto = new ProtoEntity();
        proto.setName(protoForm.getName());
        proto.setCatchcopy(protoForm.getCatchCopy());
        proto.setConcept(protoForm.getConcept());
        proto.setImage(fileName != null ? "/uploads/" + fileName : null);
        proto.setUser_name(userDetails != null ? userDetails.getUsername() : "test_user");

        try {
            protoRepository.save(proto);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "保存に失敗しました。");
            return "protos/new";
        }

        return "redirect:/";
    }

    // 投稿詳細ページ
    @GetMapping("/protos/new")
    public String newProto(Model model) {
        model.addAttribute("protoForm", new ProtoForm());
        return "protos/new";
    }

    // プロト詳細表示（コメントフォーム付き）
    @GetMapping("/protos/{protoId}")
    public String showProtoDetail(@PathVariable("protoId") Integer protoId, Model model) {
        ProtoEntity proto = protoRepository.findById(protoId);
        CommentForm commentForm = new CommentForm();
        model.addAttribute("proto", proto);
        model.addAttribute("commentForm", commentForm);
        return "protos/detail";
    }
}
