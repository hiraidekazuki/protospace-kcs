package in.tech_camp.protospace.custom_user;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetail implements UserDetails {

    private Integer id;  // 仮置きでIDだけ持つ

    public CustomUserDetail() {
        this.id = 1;  // 仮ユーザーID
    }

    public CustomUserDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    // UserDetailsインターフェースの最低限の実装（仮置き）

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();  // 権限なしの仮実装
    }

    @Override
    public String getPassword() {
        return "";  // パスワードなし（仮置き）
    }

    @Override
    public String getUsername() {
        return "dummy";  // ユーザー名仮置き
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
