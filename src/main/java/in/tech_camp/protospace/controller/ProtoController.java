package in.tech_camp.protospace.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import in.tech_camp.protospace.custom_user.CustomUserDetails;
import in.tech_camp.protospace.entity.ProtoEntity;
import in.tech_camp.protospace.form.ProtoForm;
import in.tech_camp.protospace.repository.ProtoRepository; // パッケージ名注意

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

    @GetMapping("/protos/new")
    public String showProtoNew(Model model) {
        ProtoForm dummyForm = new ProtoForm();
        dummyForm.setName("");
        dummyForm.setCatchcopy("");
        dummyForm.setConcept("");
        dummyForm.setImage("");

        model.addAttribute("protoForm", dummyForm);
        return "protos/new";
    }

    @PostMapping("/protos")
    public String createProto(@ModelAttribute("protoForm") ProtoForm protoForm) {

        ProtoEntity proto = new ProtoEntity();
        proto.setName(protoForm.getName());
        proto.setCatchcopy(protoForm.getCatchcopy());
        proto.setConcept(protoForm.getConcept());
        proto.setImage(fileName != null ? "/uploads/" + fileName : null);
        proto.setUser_name("test_user"); 

        try {
            protoRepository.insert(proto);
        } catch (Exception e) {
            System.out.println("エラー：" + e);
            return "redirect:/protos/new";

        }

        return "redirect:/";
    }
}
