package study.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.jpa.entity.Member;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsernameAndAgeGreaterThan(final String username, final int age);

    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param(value = "username") final String username);
}
