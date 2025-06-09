package in.tech_camp.protospace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.tech_camp.protospace.entity.ProtoEntity;
import in.tech_camp.protospace.form.ProtoForm;
import in.tech_camp.protospace.repository.ProtoRepository;

@Controller
public class ProtoController {

    @Autowired
    private ProtoRepository protoRepository;

    @GetMapping("/")
    public String showRoot() {
        return "protos/index";
    }

    @GetMapping("/index")
    public String showIndex(Model model) {
        return "protos/index";
    }

    @GetMapping("/protos/new")
    public String showProtoNew(Model model) {
        // 空のフォームオブジェクトをセット
        ProtoForm dummyForm = new ProtoForm();
        dummyForm.setName("");
        dummyForm.setCatchcopy("");
        dummyForm.setConcept("");
        dummyForm.setImage("");

        model.addAttribute("protoForm", dummyForm);
        return "protos/new"; // new.htmlを表示
    }

    @PostMapping("/protos")
    public String createProto(@ModelAttribute("protoForm") ProtoForm protoForm) {
        // FormからEntityに値をコピー
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
