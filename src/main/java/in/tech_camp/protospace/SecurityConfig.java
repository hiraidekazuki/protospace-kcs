package in.tech_camp.protospace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // DaoAuthenticationProviderをBean登録
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);      // ユーザー詳細サービスをセット
        authProvider.setPasswordEncoder(passwordEncoder());          // パスワードエンコーダーをセット
        return authProvider;
    }

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
                    "/users/{id}",          // 個別ユーザーページ（手動マッチ不可なので↓に統合）
                    "/users/**",            // ユーザー詳細ページなど全般
                    "/protos/**",           // プロトタイプ関連ページ
                    "/assets/**",           // 静的ファイル
                    "/packs/**"             // JSパック等
                ).permitAll()
                .requestMatchers(HttpMethod.POST, "/users").permitAll() // ユーザー登録POST
                .anyRequest().authenticated() // 他の全ては認証必須
            )
            .formLogin(form -> form
                .loginPage("/users/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            )
            // ここで認証プロバイダを指定
            .authenticationProvider(authenticationProvider());

        return http.build();
    }

    // AuthenticationManagerもBean登録（Spring Securityの認証管理用）
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
