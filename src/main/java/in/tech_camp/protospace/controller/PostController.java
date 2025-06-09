package in.tech_camp.protospace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostController {

  @GetMapping("/")
  public String showTopPage() {
      return "protos/index"; // resources/templates/protos/index.html を探す
  }
}