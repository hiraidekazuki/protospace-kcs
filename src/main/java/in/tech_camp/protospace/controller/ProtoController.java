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

    @Value("${upload.image.dir:/tmp/myapp/uploads/images}")
    private String defaultUploadDir;

    @Autowired
    public ProtoController(ProtoRepository protoRepository, ImageUrl imageUrl) {
        this.protoRepository = protoRepository;
        this.imageUrl = imageUrl;
    }

    @GetMapping("/")
    public String showIndex(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<ProtoEntity> prototypes = protoRepository.findAll();
        model.addAttribute("prototypes", prototypes);
        model.addAttribute("user", userDetails != null ? userDetails.getUserEntity() : null);
        return "protos/index";
    }

    @GetMapping("/protos/new")
    public String newProto(Model model) {
        model.addAttribute("protoForm", new ProtoForm());
        return "protos/new";
    }

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

        ProtoEntity entity = new ProtoEntity();
        entity.setName(protoForm.getName());
        entity.setCatchCopy(protoForm.getCatchCopy());
        entity.setConcept(protoForm.getConcept());

        if (userDetails != null) {
            entity.setUser(userDetails.getUserEntity());
            entity.setUserId(userDetails.getUserEntity().getId());
            entity.setUserName(userDetails.getUsername());
        } else {
            entity.setUserName("test_user");
        }

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

        try {
            protoRepository.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "保存に失敗しました。");
            return "protos/new";
        }

        return "redirect:/";
    }

    @GetMapping("/detail/{id}")
    public String showDetail(
        @PathVariable Long id,
        Model model,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        ProtoEntity proto = protoRepository.findById(id);
        if (proto == null) {
            return "redirect:/error/404";
        }

        model.addAttribute("proto", proto);
        model.addAttribute("user", userDetails != null ? userDetails.getUserEntity() : null);
        return "protos/detail";
    }

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

        // 投稿者でなければトップページへリダイレクト
        if (userDetails == null || !proto.getUserId().equals(userDetails.getUserEntity().getId())) {
            return "redirect:/";
        }

        ProtoForm protoForm = new ProtoForm();
        protoForm.setName(proto.getName());
        protoForm.setCatchCopy(proto.getCatchCopy());
        protoForm.setConcept(proto.getConcept());

        model.addAttribute("protoForm", protoForm);
        model.addAttribute("protoId", id);
        model.addAttribute("proto", proto);

        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("_csrf", csrfToken);

        return "protos/edit";
    }

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

        // 投稿者でなければトップページへリダイレクト
        if (userDetails == null || !proto.getUserId().equals(userDetails.getUserEntity().getId())) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("protoId", id);
            return "protos/edit";
        }

        proto.setName(protoForm.getName());
        proto.setCatchCopy(protoForm.getCatchCopy());
        proto.setConcept(protoForm.getConcept());

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
}
