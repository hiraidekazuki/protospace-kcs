package in.tech_camp.protospace_kcs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      //.csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(authorizeRequests -> authorizeRequests
        //以下でログアウト状態でも実行できるGETリクエストを記述する
        .requestMatchers("/css/**", "/users/sign_up", "/users/login","/images/**").permitAll()
        //以下でログアウト状態でも実行できるPOSTリクエストを記述する
        .requestMatchers(HttpMethod.POST, "/user").permitAll()
        //上記以外のリクエストは認証されたユーザーのみ許可される(要ログイン)
        .anyRequest().authenticated())

      .formLogin(login -> login
        //ログインフォームのボタンをクリックした時に送信するパス
        .loginProcessingUrl("/login")
        //ログインページのパスを設定
        .loginPage("/users/login")
        //ログイン成功後のリダイレクト先
        .defaultSuccessUrl("/users/mypage", true)//仮のマイページ
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