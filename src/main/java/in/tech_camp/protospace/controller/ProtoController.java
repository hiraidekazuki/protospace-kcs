package in.tech_camp.protospace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import in.tech_camp.protospace.form.ProtoForm;

@Controller
public class ProtoController {

    @GetMapping("/prots/new")
    public String showProtoNew(Model model) {
        // 仮のデータをセット
        ProtoForm dummyForm = new ProtoForm();
        dummyForm.setName("");
        dummyForm.setCatchcopy("");
        dummyForm.setConcept("");
        dummyForm.setImage("s"); // 画像は仮の名前

        model.addAttribute("protoForm", dummyForm);

        return "prots/new"; // new.html（テンプレート）を表示
            }
}