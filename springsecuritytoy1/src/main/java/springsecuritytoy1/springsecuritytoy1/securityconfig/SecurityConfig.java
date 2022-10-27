package springsecuritytoy1.springsecuritytoy1.securityconfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin").password(encoder.encode("1111")).roles("ADMIN", "USER", "SYS").build());
        manager.createUser(User.withUsername("user").password(encoder.encode("1111")).roles("USER").build());
        manager.createUser(User.withUsername("sys").password(encoder.encode("1111")).roles("SYS", "USER").build());

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/user").hasRole("USER")
                .antMatchers("/admin/pay").hasRole("ADMIN")
                .antMatchers("/admin/**").access("hasRole('ADMIN') or hasRole('SYS')")
                .anyRequest().authenticated();

        http
                .formLogin()
//                .loginPage("/loginPage")
                .defaultSuccessUrl("/")
                .failureForwardUrl("/login")
                .loginProcessingUrl("/login")
                .successHandler((request, response, authentication) -> {
                    log.info("authentication name = {}", authentication.getName());
                    log.info("request.getSession = {}", request.getSession().getId());

                    RequestCache requestCache = new HttpSessionRequestCache();
                    response.sendRedirect(requestCache.getRequest(request, response).getRedirectUrl());
                })
                .failureHandler((request, response, exception) -> {
                    log.info("exception.getMessage() = {}", exception.getMessage());
                    response.sendRedirect("/login");
                });

        http
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .addLogoutHandler((request, response, authentication) -> {
                    HttpSession session = request.getSession();
                    session.invalidate();
                })
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.sendRedirect("login");
                    log.info("logoutSuccessHandler complete");
                })
                .deleteCookies("remember-me");

        http
                .rememberMe()
                .rememberMeParameter("remember")
                .tokenValiditySeconds(3600)
                .userDetailsService(userDetailsService(passwordEncoder()));

        http
                .exceptionHandling()
//                .authenticationEntryPoint((request, response, authException) ->
//                        response.sendRedirect("/login"))
                .accessDeniedHandler((request, response, exception)-> {
                    log.info("loginDenied message = {}", exception.getMessage());
                    response.sendRedirect("/denied");
                });

        return http.build();
    }
}
