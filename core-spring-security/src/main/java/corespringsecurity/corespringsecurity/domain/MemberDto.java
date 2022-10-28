package corespringsecurity.corespringsecurity.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    private String username;
    private String password;
    private String email;
    private int age;
    private String role;

}
