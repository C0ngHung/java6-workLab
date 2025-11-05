package org.conghung.lab01.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {


    private static final String[] AUTH_WHITELIST = {
            "/poly/url0",
            "/login/**",
            "/access-denied",
            "/css/**",
            "/js/**",
            "/images/**",
            "/webjars/**",
            "/favicon.ico"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        // CORS có thể cấu hình chi tiết nếu cần; mặc định để nguyên CSRF (bật)
        // http.cors(AbstractHttpConfigurer::disable);
        // Phân quyền sử dụng
        http.authorizeHttpRequests(config -> {
            config.requestMatchers(AUTH_WHITELIST).permitAll();
            config.anyRequest().authenticated();
        });
        // Form đăng nhập
        http.formLogin(config -> {
            config.loginPage("/login/form");
            config.loginProcessingUrl("/login/check");
            config.defaultSuccessUrl("/login/success");
            config.failureUrl("/login/failure");
            config.permitAll();
            config.usernameParameter("username");
            config.passwordParameter("password");
        });
        // Ghi nhớ tài khoản
        http.rememberMe(config -> {
            config.userDetailsService(userDetailsService);
            config.key("change-this-to-a-strong-unique-secret");
            config.tokenValiditySeconds(3 * 24 * 60 * 60);
            config.rememberMeParameter("remember-me");
            config.rememberMeCookieName("rememberMe");
        });
        // Đăng xuất
        http.logout(config -> {
            config.logoutUrl("/logout");
            config.logoutSuccessUrl("/login/exit");
            config.clearAuthentication(true);
            config.invalidateHttpSession(true);
            config.deleteCookies("JSESSIONID", "rememberMe");
        });
        // access denied
        http.exceptionHandling(config -> {
            config.accessDeniedPage("/access-denied");
        });
        return http.build();
    }
}
