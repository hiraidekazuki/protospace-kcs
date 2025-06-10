package in.tech_camp.protospace_kcs.form;

//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserForm {

    //@NotBlank(message = "メールアドレスは必須です")
    //@Email(message = "有効なメールアドレスを入力してください")
    private String email;

    //@NotBlank(message = "パスワードは必須です")
    //@Size(min = 6, message = "パスワードは6文字以上で入力してください")
    private String password;

    private String passwordConfirmation;

    //@NotBlank(message = "ユーザー名は必須です")
    private String name;

    private String profile;

    private String group_name;

    private String post;
}