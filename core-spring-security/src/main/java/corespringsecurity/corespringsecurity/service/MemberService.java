package corespringsecurity.corespringsecurity.service;

import corespringsecurity.corespringsecurity.domain.Member;
import corespringsecurity.corespringsecurity.domain.MemberDto;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {

    void createMember(Member member);
}
