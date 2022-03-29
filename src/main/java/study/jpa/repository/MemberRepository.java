package study.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.jpa.entity.Member;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsernameAndAgeGreaterThan(final String username, final int age);
}
