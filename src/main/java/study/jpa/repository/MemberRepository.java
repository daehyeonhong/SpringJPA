package study.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.jpa.entity.Member;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsernameAndAgeGreaterThan(final String username, final int age);

    List<Member> findByUsername(@Param(value = "username") final String username);

    @Query(value = "select m from Member m where m.username=:username and m.age=:age")
    List<Member> findMember(@Param(value = "username") final String username, @Param(value = "age") final int age);
}
