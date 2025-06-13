package in.tech_camp.protospace.form;

import jakarta.validation.constraints.NotBlank;

public class CommentForm {

    @NotBlank(message = "コメントは必須です")
    private String content;

    // Getter / Setter
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
