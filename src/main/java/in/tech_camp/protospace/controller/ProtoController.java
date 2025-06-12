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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import in.tech_camp.protospace.component.ImageUrl;
import in.tech_camp.protospace.entity.ProtoEntity;
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

    // // トップ・新規投稿画面共通表示
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

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String uploadDir = imageUrl.getUrl(); // application.properties に設定されている image.url の値
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
        proto.setCatchcopy(protoForm.getCatchcopy());
        proto.setConcept(protoForm.getConcept());
        proto.setImage(fileName != null ? "/uploads/" + fileName : null);
        proto.setUserName("test_user"); 

        try {
            protoRepository.save(proto);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "保存に失敗しました。");
            return "protos/new";
        }

        return "redirect:/";
    }
}
