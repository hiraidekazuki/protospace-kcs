package in.tech_camp.protospace.controller;

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

import in.tech_camp.protospace.custom_user.CustomUserDetails;
import in.tech_camp.protospace.entity.ProtoEntity;
import in.tech_camp.protospace.form.ProtoForm;
import in.tech_camp.protospace.repository.ProtoRepository;
import jakarta.validation.Valid;

@Controller
public class ProtoController {

    private final ProtoRepository protoRepository;

    @Autowired
    public ProtoController(ProtoRepository protoRepository) {
        this.protoRepository = protoRepository;
    }

    // トップページ
    @GetMapping("/")
    public String showIndex(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<ProtoEntity> prototypes = protoRepository.findAll();
        model.addAttribute("prototypes", prototypes);
        model.addAttribute("user", userDetails != null ? userDetails.getUserEntity() : null);
        return "protos/index";
    }

    // 新規投稿ページに遷移
    @GetMapping("/protos/new")
    public String newProto(Model model) {
        model.addAttribute("protoForm", new ProtoForm());
        return "protos/new";
    }

    // プロトタイプ投稿の保存処理
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
        entity.setCatchcopy(protoForm.getCatchCopy());
        entity.setConcept(protoForm.getConcept());
        entity.setUser(userDetails.getUserEntity());

        ProtoEntity savedEntity = protoRepository.save(entity);

        // 投稿詳細ページへリダイレクト
        return "redirect:/protos/detail/" + savedEntity.getId();
    }

    // 投稿詳細ページの表示処理
    @GetMapping("/protos/detail/{id}")
    public String showDetail(@PathVariable Long id, Model model) {
        ProtoEntity proto = protoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid proto ID: " + id));
        model.addAttribute("proto", proto);
        return "protos/detail";
    }
}
