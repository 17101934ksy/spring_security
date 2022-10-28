package corespringsecurity.corespringsecurity.security.configs;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    @Bean
    protected UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) throws Exception {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();

        inMemoryUserDetailsManager.createUser(User.withUsername("user").password(passwordEncoder.encode("1111")).roles("USER").build());
        inMemoryUserDetailsManager.createUser(User.withUsername("manager").password(passwordEncoder.encode("1111")).roles("USER", "MANAGER").build());
        inMemoryUserDetailsManager.createUser(User.withUsername("admin").password(passwordEncoder.encode("1111")).roles("USER", "MANAGER", "ADMIN").build());

        return inMemoryUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() throws Exception {
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    protected SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/user").permitAll()
                .antMatchers("/mypage").hasRole("USER")
                .antMatchers("/messages").hasRole("MANAGER")
                .antMatchers("/config").hasRole("ADMIN")
                .anyRequest().authenticated();

        http
                .formLogin();

        http
                .logout()
                .deleteCookies()
                .logoutSuccessHandler(
                        (request, response, authentication) -> {
                            request.getSession().invalidate();
                            response.sendRedirect("/");
                        }
                );

        return http.build();
    }
}
