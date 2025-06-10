package in.tech_camp.protospace_kcs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // パスワードをハッシュ化するエンコーダーを Bean として登録
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

     // Spring Securityのフィルタ設定：すべてのリクエストを許可
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .anyRequest().permitAll() // すべてのリクエストを認可（ログイン不要）
            )
            .csrf().disable(); // 開発中はCSRF無効に（POSTエラー防止）

        return http.build();
    }
}