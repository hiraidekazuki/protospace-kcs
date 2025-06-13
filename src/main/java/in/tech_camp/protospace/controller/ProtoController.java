package in.tech_camp.protospace.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import in.tech_camp.protospace.component.ImageUrl;
import in.tech_camp.protospace.entity.ProtoEntity;
import in.tech_camp.protospace.repository.ProtoRepository;

@Controller
public class ProtoController {

    private final ImageUrl imageUrl;
    private final ProtoRepository protoRepository;

    @Autowired
    public ProtoController(ImageUrl imageUrl, ProtoRepository protoRepository) {
        this.imageUrl = imageUrl;
        this.protoRepository = protoRepository;
    }

     @GetMapping("/")
    public String showIndex(Model model) {
        List<ProtoEntity> prototypes = protoRepository.findAll();
        model.addAttribute("prototypes", prototypes);

        return "protos/index";
    }

    // 投稿作成処理
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