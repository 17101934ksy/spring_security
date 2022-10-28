package corespringsecurity.corespringsecurity.repository;

import corespringsecurity.corespringsecurity.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
