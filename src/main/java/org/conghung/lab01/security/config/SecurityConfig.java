package org.conghung.lab01.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
    public UserDetailsService userDetailsService(PasswordEncoder pe) {
        String password = pe.encode("123");

        UserDetails user1 = User.withUsername("user1").password(password).roles("USER").build();
        UserDetails user2 = User.withUsername("user2").password(password).roles("USER").build();
        UserDetails user3 = User.withUsername("user3").password(password).roles("USER").build();
        UserDetails user4 = User.withUsername("user@gmail.com").password(password).roles("USER").build();
        UserDetails user5 = User.withUsername("admin@gmail.com").password(password).roles("ADMIN").build();
        UserDetails user6 = User.withUsername("both@gmail.com").password(password).roles("USER", "ADMIN").build();

        return new InMemoryUserDetailsManager(user1, user2, user3, user4, user5, user6);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
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
