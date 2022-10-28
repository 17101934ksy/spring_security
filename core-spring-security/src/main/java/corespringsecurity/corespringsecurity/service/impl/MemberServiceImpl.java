package corespringsecurity.corespringsecurity.service.impl;

import corespringsecurity.corespringsecurity.domain.Member;
import corespringsecurity.corespringsecurity.repository.MemberRepository;
import corespringsecurity.corespringsecurity.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void createMember(Member member) {

        member.updatePassword(passwordEncoder.encode(member.getPassword()));

        memberRepository.save(member);

    }
}
