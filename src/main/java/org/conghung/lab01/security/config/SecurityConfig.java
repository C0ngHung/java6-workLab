package org.conghung.lab01.security.config;

/*
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

@Slf4j
@Configuration
@EnableWebSecurity
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
            "/favicon.ico",
            "/student-crud"
    };

    private static final String REMEMBER_ME_KEY = "lab01-remember-me-secret-key-2024-change-in-production";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService userDetailsService)
            throws Exception {

        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(AUTH_WHITELIST).permitAll();
            auth.anyRequest().authenticated();
        });

        // OAuth2 (Google Login)
        http.oauth2Login(oauth -> {
            oauth.loginPage("/login/form");
            oauth.permitAll();
            oauth.successHandler(createOAuth2SuccessHandler());
        });

        // Form login
        http.formLogin(form -> {
            form.loginPage("/login/form");
            form.loginProcessingUrl("/login/check");
            form.successHandler(createFormLoginSuccessHandler());
            form.failureUrl("/login/failure");
            form.usernameParameter("username");
            form.passwordParameter("password");
            form.permitAll();
        });

        // Remember-me
        http.rememberMe(remember -> {
            remember.userDetailsService(userDetailsService);
            remember.key(REMEMBER_ME_KEY);
            remember.tokenValiditySeconds(3 * 24 * 60 * 60); // 3 ngày
            remember.rememberMeParameter("remember-me");
            remember.rememberMeCookieName("rememberMe");
        });

        // Logout
        http.logout(logout -> {
            logout.logoutUrl("/logout");
            logout.logoutSuccessUrl("/login/exit");
            logout.invalidateHttpSession(true);
            logout.clearAuthentication(true);
            logout.deleteCookies("JSESSIONID", "rememberMe");
        });

        // Exception handling - sử dụng AccessDeniedHandler thay vì deprecated accessDeniedPage
        http.exceptionHandling(e -> e.accessDeniedHandler(createAccessDeniedHandler()));

        // CSRF protection được bật mặc định trong Spring Security 6
        // Session management mặc định đã được cấu hình

        return http.build();
    }

    private AuthenticationSuccessHandler createOAuth2SuccessHandler() {
        return (request, response, authentication) -> {
            try {
                Object principal = authentication.getPrincipal();

                // Lấy email cho cả OIDC (Google với scope openid) và OAuth2 thường
                String username = null;
                if (principal instanceof OidcUser oidcUser) {
                    username = oidcUser.getEmail();
                } else if (principal instanceof DefaultOAuth2User oAuth2User) {
                    // Google sẽ cung cấp thuộc tính "email" khi yêu cầu scope email
                    Object emailAttr = oAuth2User.getAttribute("email");
                    username = emailAttr != null ? String.valueOf(emailAttr) : null;
                } else {
                    log.error("Principal không hỗ trợ: {}", principal.getClass());
                }

                if (username == null || username.isEmpty()) {
                    log.error("Không thể lấy email từ OAuth2 user");
                    response.sendRedirect("/login/failure");
                    return;
                }

                // Tạo UserDetails với role OAUTH
                UserDetails localUser = User.withUsername(username)
                        .password("{noop}")
                        .roles("OAUTH")
                        .build();

                Authentication newAuth = new UsernamePasswordAuthenticationToken(
                        localUser,
                        null,
                        localUser.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(newAuth);

                String redirectUrl = getRedirectUrl(request);
                log.info("OAuth2 login thành công cho user: {}, redirect to: {}", username, redirectUrl);
                response.sendRedirect(redirectUrl);

            } catch (Exception e) {
                log.error("Lỗi khi xử lý OAuth2 success handler", e);
                response.sendRedirect("/login/failure");
            }
        };
    }

    private AuthenticationSuccessHandler createFormLoginSuccessHandler() {
        SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();
        handler.setDefaultTargetUrl("/login/success");
        handler.setAlwaysUseDefaultTargetUrl(false); // Cho phép redirect về saved request
        return handler;
    }

    private AccessDeniedHandler createAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            log.warn("Access denied cho user: {} tại URL: {}", 
                    SecurityContextHolder.getContext().getAuthentication() != null 
                            ? SecurityContextHolder.getContext().getAuthentication().getName() 
                            : "anonymous",
                    request.getRequestURI());
            response.sendRedirect("/access-denied");
        };
    }

    private String getRedirectUrl(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            DefaultSavedRequest savedRequest = 
                    (DefaultSavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
            if (savedRequest != null && savedRequest.getRedirectUrl() != null) {
                return savedRequest.getRedirectUrl();
            }
        }
        return "/login/success";
    }
}
*/
