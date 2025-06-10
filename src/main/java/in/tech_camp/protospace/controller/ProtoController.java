package in.tech_camp.protospace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.tech_camp.protospace.entity.ProtoEntity;
import in.tech_camp.protospace.form.ProtoForm;
import in.tech_camp.protospace.repository.ProtoRepository;
import jakarta.validation.Valid;

@Controller
public class ProtoController {

    @Autowired
    private ProtoRepository protoRepository;

    // ルートは新規投稿画面にリダイレクト（または表示）
    @GetMapping("/")
    public String showRoot(Model model) {
        return showNewForm(model);
    }

    // /new も新規投稿画面表示に統一
    @GetMapping("/new")
    public String showIndex(Model model) {
        return showNewForm(model);
    }

    // 新規投稿フォーム表示用の共通メソッド
    private String showNewForm(Model model) {
        ProtoForm protoForm = new ProtoForm();
        protoForm.setName("");
        protoForm.setCatchcopy("");
        protoForm.setConcept("");
        // imageはファイルアップロードなので初期値は特に必要なし
        model.addAttribute("protoForm", protoForm);
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
            // バリデーションエラーあればフォームに戻る
            return "protos/new";
        }

        ProtoEntity proto = new ProtoEntity();
        proto.setName(protoForm.getName());
        proto.setCatchcopy(protoForm.getCatchcopy());
        proto.setConcept(protoForm.getConcept());
        proto.setImage(protoForm.getImage());

        try {
            protoRepository.save(proto);
        } catch (Exception e) {
            System.out.println("エラー：" + e);
            // 保存失敗時は新規投稿画面へリダイレクト
            return "redirect:/protos/new";
        }

        // 保存成功したらトップへリダイレクト（必要に応じて変更可能）
        return "redirect:/";
    }
}
