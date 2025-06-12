package in.tech_camp.protospace;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(authz -> authz
    .requestMatchers("/css/**", "/images/**", "/uploads/**", "/", "/users/sign_up", "/users/login").permitAll()
    .requestMatchers(HttpMethod.POST, "/user").permitAll()
    .requestMatchers("/protos/**").permitAll()
    .requestMatchers("/protos/*/edit", "/protos/*/delete").authenticated()
    .anyRequest().authenticated()
)
        .csrf(csrf -> csrf.disable());
    return http.build();
}

}


//     @Bean
// public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//     http
//         .authorizeHttpRequests(authz -> authz
//             .anyRequest().permitAll()  // すべて許可
//         )
//         .csrf().disable(); // 必要ならCSRF無効も
//     return http.build();
// }


    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http
    //         .authorizeHttpRequests(authorize -> authorize
    //             .requestMatchers(
    //                 "/css/**",
    //                 "/images/**",
    //                 "/",
    //                 "/users/sign_up",
    //                 "/users/login",
    //                 "/protos/*"
    //             ).permitAll()
    //             .requestMatchers(HttpMethod.POST, "/users").permitAll()
    //             .anyRequest().authenticated()
    //         )
    //         .formLogin(form -> form
    //             .loginPage("/users/login")
    //             .defaultSuccessUrl("/", true)
    //             .permitAll()
    //         )
    //         .logout(logout -> logout
    //             .logoutSuccessUrl("/")
    //             .permitAll()
    //         );
            
    //     return http.build();
    // }
