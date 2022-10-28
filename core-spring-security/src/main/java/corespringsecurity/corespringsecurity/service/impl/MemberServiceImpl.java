package corespringsecurity.corespringsecurity.service.impl;

import corespringsecurity.corespringsecurity.domain.Member;
import corespringsecurity.corespringsecurity.repository.MemberRepository;
import corespringsecurity.corespringsecurity.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void createMember(Member member) {

        memberRepository.save(member);

    }
}
