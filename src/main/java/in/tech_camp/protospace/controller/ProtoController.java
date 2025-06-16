package in.tech_camp.protospace.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import in.tech_camp.protospace.custom_user.CustomUserDetails;
import in.tech_camp.protospace.entity.ProtoEntity;
import in.tech_camp.protospace.repository.ProtoRepository;

@Controller
public class ProtoController {

    private final ProtoRepository protoRepository;

    @Autowired
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
}