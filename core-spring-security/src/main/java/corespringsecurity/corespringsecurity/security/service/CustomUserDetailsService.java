package corespringsecurity.corespringsecurity.security.service;

import corespringsecurity.corespringsecurity.domain.Member;
import corespringsecurity.corespringsecurity.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> findMember = memberRepository.findByUsername(username);

        Member member = findMember.orElseThrow(
                () -> {
                    throw new UsernameNotFoundException("UsernameNotFoundException");
                }
        );

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(String.valueOf(member.getRole())));

        MemberContext memberContext = new MemberContext(member, roles);

        log.info("memberContext.getMember().getRole() = {}", memberContext.getMember().getRole());

        return memberContext;
    }
}
