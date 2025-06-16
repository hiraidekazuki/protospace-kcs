package in.tech_camp.protospace.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import in.tech_camp.protospace.custom_user.CustomUserDetail;
import in.tech_camp.protospace.entity.CommentEntity;
import in.tech_camp.protospace.entity.ProtoEntity;
import in.tech_camp.protospace.entity.UserEntity;
import in.tech_camp.protospace.form.CommentForm;
import in.tech_camp.protospace.form.ProtoForm;
import in.tech_camp.protospace.repository.CommentRepository;
import in.tech_camp.protospace.repository.ProtoRepository;

@Controller
public class ProtoController {

    private final CommentRepository commentRepository;
    private final ProtoRepository protoRepository;

    @Value("${upload.dir}") // アプリケーションの設定からディレクトリを取得
    private String uploadDir;

    @Value("${image.url}") // 画像URLの設定（静的リソースとして提供されるパス）
    private String imageUrl;

    public ProtoController(ProtoRepository protoRepository, CommentRepository commentRepository) {
        this.protoRepository = protoRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping({"/", "/protos"})
    public String showIndex(Model model) {
        List<ProtoEntity> prototypes = protoRepository.findAll();
        model.addAttribute("prototypes", prototypes);
        return "protos/index";
    }

    @GetMapping("/protos/new")
    public String showNewProtoForm(Model model) {
        model.addAttribute("protoForm", new ProtoForm());
        return "protos/new";
    }

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

        // 画像ファイルがアップロードされた場合の処理
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // UUIDを付与してファイル名をユニークにする
                String uniqueFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path imagePath = Paths.get(uploadDir, uniqueFileName);
                
                // 画像保存先ディレクトリを作成
                Files.createDirectories(imagePath.getParent());
                
                // 画像を保存
                Files.copy(imageFile.getInputStream(), imagePath);
                
                // 画像のパス（URL）を設定
                fileName = imageUrl + uniqueFileName; // 例: /uploads/ユニークファイル名
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("error", "画像の保存に失敗しました。");
                return "protos/new";
            }
        }

        // ProtoEntityに新しいプロトタイプ情報を保存
        ProtoEntity proto = new ProtoEntity();
        proto.setName(protoForm.getName());
        proto.setCatchCopy(protoForm.getCatchCopy());
        proto.setConcept(protoForm.getConcept());
        proto.setImage(fileName); // 画像URLを保存

        // ユーザー情報を設定
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetail) {
            CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
            proto.setUserId(userDetails.getId());
        } else {
            proto.setUserId(0); // 未認証ユーザー対応（仮）
        }

        try {
            // プロトタイプをデータベースに保存
            protoRepository.save(proto);
            return "redirect:/"; // トップページへリダイレクト
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "保存に失敗しました。");
            return "protos/new";
        }
    }

    @GetMapping("/protos/{protoId}")
    public String showProtoDetail(@PathVariable("protoId") Integer protoId, Model model) {
        ProtoEntity proto = protoRepository.findById(protoId);
        if (proto == null) {
            model.addAttribute("error", "指定された投稿が見つかりません。");
            return "error";
        }

        // コメントの設定
        List<CommentEntity> comments = commentRepository.findByProtoId(protoId);
        proto.setComments(comments);

        // ユーザー情報がnullの場合は仮ユーザーを設定
        if (proto.getUser() == null) {
            UserEntity dummyUser = new UserEntity();
            dummyUser.setId(0);
            dummyUser.setName("仮ユーザー");
            proto.setUser(dummyUser);
        }

        model.addAttribute("commentForm", new CommentForm());
        model.addAttribute("proto", proto);

        return "protos/detail";
    }
}
