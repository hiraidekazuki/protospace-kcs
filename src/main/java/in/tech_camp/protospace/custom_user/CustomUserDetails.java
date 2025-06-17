package in.tech_camp.protospace.custom_user;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import in.tech_camp.protospace.entity.UserEntity;

public class CustomUserDetails implements UserDetails {

    private final UserEntity user;

    public CustomUserDetails(UserEntity user) {
        this.user = user;
    }

    public UserEntity getUserEntity() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // 権限は扱わない
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // エンコードされたパスワード
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // ログインIDとして使う
    }

      public long getId() {
      return user.getId();
    }

    public String getName() {
      return user.getName();
    }




    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
