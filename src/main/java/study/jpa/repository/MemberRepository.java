package study.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.jpa.dto.MemberDto;
import study.jpa.entity.Member;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsernameAndAgeGreaterThan(final String username, final int age);

    List<Member> findByUsername(@Param(value = "username") final String username);

    @Query(value = "select m from Member m where m.username=:username and m.age=:age")
    List<Member> findMember(@Param(value = "username") final String username, @Param(value = "age") final int age);

    @Query(value = "select m.username from Member m")
    List<String> findUsernameList();

    @Query(value = "select new study.jpa.dto.MemberDto(m.username,m.age,t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query(value = "select m from Member m where m.username in :usernames")
    List<Member> findByUsernames(@Param(value = "usernames") final Collection<String> usernames);
//    List<Member> findByUsername(@Param(value = "usernames") final String[] usernames); 배열도 사용 가능

    List<Member> findListByUsername(final String username);//컬렉선

    Member findMemberByUsername(final String username);//단건

    Optional<Member> findOptionalMemberByUsername(final String username);//단건 Optional
}
