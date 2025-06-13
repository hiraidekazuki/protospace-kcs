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

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http

      // CSRFを無効化（必要に応じてコメントアウトしてください）
      //.csrf(csrf -> csrf.disable())

      //.csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(authorizeRequests -> authorizeRequests
        //以下でログアウト状態でも実行できるGETリクエストを記述する
        .requestMatchers("/css/**", "/images/**", "/uploads/**", "/", "/users/sign_up", "/users/login").permitAll()
        //以下でログアウト状態でも実行できるPOSTリクエストを記述する
        .requestMatchers(HttpMethod.POST, "/users").permitAll()
        //上記以外のリクエストは認証されたユーザーのみ許可される(要ログイン)
        // 認証が必要な編集・削除系の操作
        .requestMatchers("/protos/*/edit", "/protos/*/delete").authenticated()
        // その他の /protos 配下のリクエストは全て許可
        .requestMatchers("/protos/**").permitAll()

        .anyRequest().authenticated())

      .formLogin(login -> login
        //ログインフォームのボタンをクリックした時に送信するパス
        .loginProcessingUrl("/login")
        //ログインページのパスを設定
        .loginPage("/users/login")
        //ログイン成功後のリダイレクト先
        .defaultSuccessUrl("/protos", true)
        //ログイン失敗後のリダイレクト先
        .failureUrl("/login?error")
        .usernameParameter("email")
        .permitAll())

      .logout(logout -> logout
        .logoutUrl("/logout")
        //ログアウト成功時のリダイレクト先
        .logoutSuccessUrl("/users/login"));

      return http.build();
    }

    // パスワードをハッシュ化するエンコーダーを Bean として登録
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}