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

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // ヘルパー：正しいフォームを作成する
    private UserForm createValidForm() {
        UserForm form = new UserForm();
        form.setEmail("test@example.com");
        form.setPassword("abcdef");
        form.setPasswordConfirmation("abcdef");
        form.setName("太郎");
        form.setProfile("桃太郎");
        form.setGroupName("岡山");
        form.setPost("桃太郎");
        return form;
    }

    @Test
    void 正常な入力ならバリデーションエラーがないこと() {
        UserForm form = createValidForm();
        Set<ConstraintViolation<UserForm>> violations = validator.validate(form);

        BindingResult result = new BeanPropertyBindingResult(form, "userForm");
        form.validatePasswordConfirmation(result);

        assertTrue(violations.isEmpty());
        assertFalse(result.hasErrors());
    }

    @Test
    void メールアドレスが空だとエラーになる() {
        UserForm form = createValidForm();
        form.setEmail("");

        Set<ConstraintViolation<UserForm>> violations = validator.validate(form);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void メールアドレスが不正形式だとエラーになる() {
        UserForm form = createValidForm();
        form.setEmail("invalid-email");

        Set<ConstraintViolation<UserForm>> violations = validator.validate(form);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void パスワードが空だとエラーになる() {
        UserForm form = createValidForm();
        form.setPassword("");

        Set<ConstraintViolation<UserForm>> violations = validator.validate(form);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

    @Test
    void パスワードが6文字未満だとエラーになる() {
        UserForm form = createValidForm();
        form.setPassword("123");
        form.setPasswordConfirmation("123");

        Set<ConstraintViolation<UserForm>> violations = validator.validate(form);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

    @Test
    void パスワード確認が空だとエラーになる() {
        UserForm form = createValidForm();
        form.setPasswordConfirmation("");

        Set<ConstraintViolation<UserForm>> violations = validator.validate(form);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("passwordConfirmation")));
    }

    @Test
    void パスワードと確認が一致しないとエラーになる() {
        UserForm form = createValidForm();
        form.setPassword("abcdef");
        form.setPasswordConfirmation("ghijkl");

        BindingResult result = new BeanPropertyBindingResult(form, "userForm");
        form.validatePasswordConfirmation(result);

        assertTrue(result.hasFieldErrors("passwordConfirmation"), "パスワード確認が一致しないエラー");
    }

    @Test
    void ユーザー名が空だとエラーになる() {
        UserForm form = createValidForm();
        form.setName("");

        Set<ConstraintViolation<UserForm>> violations = validator.validate(form);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void プロフィールが空だとエラーになる() {
        UserForm form = createValidForm();
        form.setProfile("");

        Set<ConstraintViolation<UserForm>> violations = validator.validate(form);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("profile")));
    }

    @Test
    void 所属が空だとエラーになる() {
        UserForm form = createValidForm();
        form.setGroupName("");

        Set<ConstraintViolation<UserForm>> violations = validator.validate(form);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("groupName")));
    }

    @Test
    void 役職が空だとエラーになる() {
        UserForm form = createValidForm();
        form.setPost("");

        Set<ConstraintViolation<UserForm>> violations = validator.validate(form);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("post")));
    }
}