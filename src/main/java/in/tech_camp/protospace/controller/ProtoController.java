package in.tech_camp.protospace.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.tech_camp.protospace.custom_user.CustomUserDetails;
import in.tech_camp.protospace.entity.ProtoEntity;
import in.tech_camp.protospace.form.ProtoForm;
import in.tech_camp.protospace.repository.ProtoRepository;

@Controller
public class ProtoController {

    @Autowired
    private ProtoRepository protoRepository;

    // トップ・新規投稿画面共通表示
    @GetMapping({"/", "/index"})
    public String showNewForm(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<ProtoEntity> prototypes = protoRepository.findAll();

        model.addAttribute("prototypes", prototypes);
        model.addAttribute("protoForm", new ProtoForm());

        if (userDetails != null) {
            model.addAttribute("user", userDetails.getUserEntity());  // ← ここを修正
        }

        return "protos/index";
    }

// 投稿ページ（新規投稿表示）
    @GetMapping("/new")
        public String showNewForm(Model model) {
            model.addAttribute("protoForm", new ProtoForm());
        return "protos/new";
    }

    @PostMapping("/protos")
    public String createProto(@ModelAttribute("protoForm") ProtoForm protoForm) {
        ProtoEntity proto = new ProtoEntity();
        proto.setName(protoForm.getName());
        proto.setCatchcopy(protoForm.getCatchcopy());
        proto.setConcept(protoForm.getConcept());
        proto.setImage(protoForm.getImage());

        try {
            protoRepository.insert(proto);
        } catch (Exception e) {
            System.out.println("エラー：" + e);
            return "redirect:/protos/new";
        }

        return "redirect:/";
    }
}
