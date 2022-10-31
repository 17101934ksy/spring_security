package corespringsecurity.corespringsecurity.security.provider;

import corespringsecurity.corespringsecurity.security.common.FormWebAuthenticationDetails;
import corespringsecurity.corespringsecurity.security.service.CustomUserDetailsService;
import corespringsecurity.corespringsecurity.security.service.MemberContext;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("authenticationProvider")
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {


    private final CustomUserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        MemberContext memberContext = (MemberContext) userDetailsService.loadUserByUsername(authentication.getName());

        if (!passwordEncoder.matches((String) authentication.getCredentials(), memberContext.getMember().getPassword()))
            throw new BadCredentialsException("BadCredentialException");

        FormWebAuthenticationDetails formWebAuthenticationDetails = (FormWebAuthenticationDetails) authentication.getDetails();
        String secretKey = formWebAuthenticationDetails.getSecretKey();

        if (!"secret".equals(secretKey)) {
            throw new InsufficientAuthenticationException("InsufficientAuthenticationException");
        }

        return  new UsernamePasswordAuthenticationToken(
                memberContext.getMember(), null, memberContext.getAuthorities()
        );
    }


    // 파라미터로 전달되는 타입과 클래스가 사용하고자 하는 토큰의 타입이 일치할 때
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom( authentication );
    }
}
