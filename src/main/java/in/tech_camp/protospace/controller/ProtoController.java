package in.tech_camp.protospace.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import in.tech_camp.protospace.custom_user.CustomUserDetails;
import in.tech_camp.protospace.entity.ProtoEntity;
import in.tech_camp.protospace.form.ProtoForm;
import in.tech_camp.protospace.repository.ProtoRepository;
import jakarta.validation.Valid;

@Controller
public class ProtoController {

    private final ProtoRepository protoRepository;

    @Value("${upload.image.dir:/tmp/myapp/uploads/images}")
    private String uploadDir;

    public ProtoController(ProtoRepository protoRepository) {
        this.protoRepository = protoRepository;
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
        entity.setUser(userDetails.getUserEntity());
        entity.setUserId(userDetails.getUserEntity().getId());

        MultipartFile imageFile = protoForm.getImage();
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();

                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists() && !uploadDirFile.mkdirs()) {
                    bindingResult.rejectValue("image", "upload.failed", "アップロードディレクトリの作成に失敗しました");
                    return "protos/new";
                }

                File destFile = new File(uploadDirFile, fileName);
                imageFile.transferTo(destFile);

                entity.setImage("/uploads/images/" + fileName);

            } catch (IOException e) {
                e.printStackTrace();
                bindingResult.rejectValue("image", "upload.failed", "画像のアップロードに失敗しました");
                return "protos/new";
            }
        }

        protoRepository.save(entity);

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
            // 404ページなどにリダイレクトする例
            return "redirect:/error/404";
        }

        model.addAttribute("proto", proto);
        model.addAttribute("user", userDetails != null ? userDetails.getUserEntity() : null);
        return "protos/detail";
    }

    @PostMapping("/protos/{id}/delete")
    public String deleteProto(
        @PathVariable Long id,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        ProtoEntity proto = protoRepository.findById(id);
        if (proto == null) {
            return "redirect:/error/404";
        }

        if (!proto.getUser().getId().equals(userDetails.getUserEntity().getId())) {
            throw new SecurityException("You are not authorized to delete this post.");
        }

        protoRepository.deleteById(id);
        return "redirect:/";
    }
}
