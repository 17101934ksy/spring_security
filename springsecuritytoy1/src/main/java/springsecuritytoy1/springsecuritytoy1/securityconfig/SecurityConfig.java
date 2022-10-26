package springsecuritytoy1.springsecuritytoy1.securityconfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/ignore1", "/ignore2");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()

        .and()
                .formLogin()
//                .loginPage("/loginPage")
                .defaultSuccessUrl("/")
                .failureForwardUrl("/login")
                .loginProcessingUrl("/login")
                .successHandler((request, response, authentication) -> {
                    System.out.println("authentication = " + authentication.getName());
                    response.sendRedirect("/");
                })
                .failureHandler((request, response, exception) -> {
                    System.out.println("exception.getMessage() = " + exception.getMessage());
                    response.sendRedirect("/login");
                })
                .permitAll()

        .and()
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
                .deleteCookies("remember-me")

        .and()
                .rememberMe()
                .rememberMeParameter("remember")
                .tokenValiditySeconds(3600)
                .userDetailsService(userDetailsService);

        return http.build();
    }
}
