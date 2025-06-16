package in.tech_camp.protospace.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import in.tech_camp.protospace.custom_user.CustomUserDetail;
import in.tech_camp.protospace.entity.CommentEntity;
import in.tech_camp.protospace.entity.ProtoEntity;
import in.tech_camp.protospace.form.CommentForm;
import in.tech_camp.protospace.repository.CommentRepository;
import in.tech_camp.protospace.repository.ProtoRepository;
import in.tech_camp.protospace.repository.UserRepository;

@Controller
public class CommentController {

    private final CommentRepository commentRepository;
    private final ProtoRepository protoRepository;
    private final UserRepository userRepository;

    // 手動コンストラクタを追加
    public CommentController(CommentRepository commentRepository,
                             ProtoRepository protoRepository,
                             UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.protoRepository = protoRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/protos/{protoId}/comment")
    public String createComment(@PathVariable("protoId") Integer protoId,
                                @ModelAttribute("commentForm") @Validated CommentForm commentForm,
                                BindingResult result,
                                @AuthenticationPrincipal CustomUserDetail currentUser,
                                Model model) {

        ProtoEntity proto = protoRepository.findById(protoId);
        if (proto == null) {
            model.addAttribute("error", "指定されたプロトタイプが見つかりません。");
            return "error";
        }

        if (result.hasErrors()) {
            model.addAttribute("errorMessages", result.getAllErrors());
            model.addAttribute("proto", proto);
            model.addAttribute("commentForm", commentForm);
            return "protos/detail";
        }

        CommentEntity comment = new CommentEntity();
        comment.setContent(commentForm.getContent());
        comment.setProto(proto);
        comment.setUser(userRepository.findById(currentUser.getId()));

        try {
            commentRepository.insert(comment);
        } catch (Exception e) {
            model.addAttribute("proto", proto);
            model.addAttribute("commentForm", commentForm);
            model.addAttribute("error", "コメントの保存に失敗しました。");
            e.printStackTrace();
            return "protos/detail";
        }

        return "redirect:/protos/" + protoId;
    }
}
