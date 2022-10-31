package corespringsecurity.corespringsecurity.controller.member;

import corespringsecurity.corespringsecurity.domain.Member;
import corespringsecurity.corespringsecurity.domain.MemberDto;
import corespringsecurity.corespringsecurity.domain.RoleType;
import corespringsecurity.corespringsecurity.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public String createMember() {
        return "user/login/register";
    }

    @PostMapping
    public String createMember(MemberDto memberDto) {

        Member member = Member.builder()
                .username(memberDto.getUsername())
                .password(memberDto.getPassword())
                .email(memberDto.getEmail())
                .age(memberDto.getAge())
                .role(RoleType.USER)
                .build();

        log.info("member.email = {}", member.getEmail());
        log.info("member.age = {}", member.getAge());
        log.info("member.username = {}", member.getUsername());
        log.info("member.role = {}", member.getRole());

        memberService.createMember(member);

        return "redirect:/";
    }

}
