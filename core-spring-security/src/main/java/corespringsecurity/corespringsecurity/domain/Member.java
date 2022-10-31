package corespringsecurity.corespringsecurity.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private String password;
    private String email;
    private int age;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    public Member (String username, String password, String email, RoleType role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateMemberInfo(int age) {
        this.age = age;
    }

}
