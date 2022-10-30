package corespringsecurity.corespringsecurity.security.provider;

import corespringsecurity.corespringsecurity.security.service.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        MemberContext memberContext = (MemberContext) userDetailsService.loadUserByUsername(authentication.getName());

        if (!passwordEncoder.matches((String) authentication.getCredentials(), memberContext.getMember().getPassword()))
            throw new BadCredentialsException("BadCredentialException");

        return  new UsernamePasswordAuthenticationToken(
                memberContext.getMember(), null, memberContext.getAuthorities()
        );
    }


    // 파라미터로 전달되는 타입과 클래스가 사용하고자 하는 토큰의 타입이 일치할 때
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
