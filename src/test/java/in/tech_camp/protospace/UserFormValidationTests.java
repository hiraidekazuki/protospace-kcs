package in.tech_camp.protospace;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import in.tech_camp.protospace.form.UserForm;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class UserFormValidationTests {

    private Validator validator;
    private UserForm form;
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        form = new UserForm();
        form.setEmail("test@example.com");
        form.setPassword("abcdef");
        form.setPasswordConfirmation("abcdef");
        form.setName("太郎");
        form.setProfile("桃太郎");
        form.setGroupName("岡山");
        form.setPost("桃太郎");

        bindingResult = new BeanPropertyBindingResult(form, "userForm");
    }

    @Test
    void 正常な入力ならバリデーションエラーがないこと() {
        Set<ConstraintViolation<UserForm>> violations = validator.validate(form);
        form.validatePasswordConfirmation(bindingResult);

        assertTrue(violations.isEmpty());
        assertFalse(bindingResult.hasErrors());
    }

    @Test
    void メールアドレスが空だとエラーになる() {
        form.setEmail("");
        Set<ConstraintViolation<UserForm>> violations = validator.validate(form);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")
            && v.getMessage().contains("メールアドレスは必須です")));
    }

    @Test
    void メールアドレスが不正形式だとエラーになる() {
        form.setEmail("invalid-email");
        Set<ConstraintViolation<UserForm>> violations = validator.validate(form);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")
            && v.getMessage().contains("有効なメールアドレスを入力してください")));
    }

    @Test
    void パスワードが空だとエラーになる() {
        form.setPassword("");
        Set<ConstraintViolation<UserForm>> violations = validator.validate(form);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")
            && v.getMessage().contains("パスワードは必須です")));
    }

    @Test
    void パスワードが6文字未満だとエラーになる() {
        form.setPassword("123");
        form.setPasswordConfirmation("123");
        Set<ConstraintViolation<UserForm>> violations = validator.validate(form);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")
            && v.getMessage().contains("パスワードは6文字以上で入力してください")));
    }

    @Test
    void パスワード確認が空だとエラーになる() {
        form.setPasswordConfirmation("");
        Set<ConstraintViolation<UserForm>> violations = validator.validate(form);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("passwordConfirmation")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("passwordConfirmation")
            && v.getMessage().contains("パスワード（確認）は必須です")));
    }

    @Test
    void パスワードと確認が一致しないとエラーになる() {
        form.setPassword("abcdef");
        form.setPasswordConfirmation("ghijkl");
        form.validatePasswordConfirmation(bindingResult);

        assertTrue(bindingResult.hasFieldErrors("passwordConfirmation"));
        assertTrue(bindingResult.getFieldErrors("passwordConfirmation").stream()
            .anyMatch(e -> e.getDefaultMessage().contains("パスワードが一致しません")));
    }

    @Test
    void ユーザー名が空だとエラーになる() {
        form.setName("");
        Set<ConstraintViolation<UserForm>> violations = validator.validate(form);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")
            && v.getMessage().contains("ユーザー名は必須です")));
    }

    @Test
    void プロフィールが空だとエラーになる() {
        form.setProfile("");
        Set<ConstraintViolation<UserForm>> violations = validator.validate(form);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("profile")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("profile")
            && v.getMessage().contains("プロフィールは必須です")));
    }

    @Test
    void 所属が空だとエラーになる() {
        form.setGroupName("");
        Set<ConstraintViolation<UserForm>> violations = validator.validate(form);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("groupName")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("groupName")
            && v.getMessage().contains("所属は必須です")));
    }

    @Test
    void 役職が空だとエラーになる() {
        form.setPost("");
        Set<ConstraintViolation<UserForm>> violations = validator.validate(form);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("post")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("post")
            && v.getMessage().contains("役職は必須です")));
    }
}
