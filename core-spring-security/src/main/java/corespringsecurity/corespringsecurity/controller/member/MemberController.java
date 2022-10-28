package corespringsecurity.corespringsecurity.controller.member;

import corespringsecurity.corespringsecurity.configuration.UtilConfig;
import corespringsecurity.corespringsecurity.domain.Member;
import corespringsecurity.corespringsecurity.domain.MemberDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {


    @GetMapping
    public String createMember() {
        return "user/login/register";
    }

    @PostMapping
    public String createMember(MemberDto memberDto) {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(memberDto, Member.class);

        return "redirect:/";
    }

}
