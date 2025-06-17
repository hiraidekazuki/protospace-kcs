package in.tech_camp.protospace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import in.tech_camp.protospace.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/",                    // トップページ
                    "/css/**",              // CSSファイル
                    "/images/**",           // 画像ファイル
                    "/users/login",         // ログインページ
                    "/users/sign_up",       // サインアップページ
                    "/users/**",            // ユーザー詳細ページなど
                    "/detail/**",           // プロトタイプ詳細ページ（誰でも閲覧可能）
                    "/uploads/**",          // 画像ファイルアクセス用
                    "/assets/**",           // 静的ファイル
                    "/packs/**"             // JSパック等
                ).permitAll()
                .requestMatchers("/protos/new").authenticated()  // 投稿ページは認証必須
                .requestMatchers(HttpMethod.POST, "/users").permitAll() // ユーザー登録POSTは許可
                .anyRequest().authenticated() // その他のリクエストは認証必須
            )
            .formLogin(form -> form
                .loginPage("/users/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .usernameParameter("email")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
