package in.tech_camp.protospace;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // CSRF無効化（必要に応じて設定）
            .csrf(AbstractHttpConfigurer::disable)

            // アクセス許可の設定
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", 
                    "/css/**", 
                    "/images/**", 
                    "/users/sign_up", 
                    "/users/login", 
                    "/tweets/{id:[0-9]+}", 
                    "/users/{id:[0-9]+}", 
                    "/tweets/search"
                ).permitAll()
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .anyRequest().authenticated()
            )
            
            // ログイン設定
            .formLogin(form -> form
                .loginProcessingUrl("/login")
                .loginPage("/users/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/users/login?error")
                .usernameParameter("email")
                .permitAll()
            )
            
            // ログアウト設定
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
}
