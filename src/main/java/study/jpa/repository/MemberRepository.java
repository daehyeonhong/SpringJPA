package study.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import study.jpa.dto.MemberDto;
import study.jpa.entity.Member;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import static javax.persistence.LockModeType.PESSIMISTIC_WRITE;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsernameAndAgeGreaterThan(final String username, final int age);

    List<Member> findByUsername(@Param(value = "username") final String username);

    @Query(value = "select m from Member m where m.username=:username and m.age=:age")
    List<Member> findMember(@Param(value = "username") final String username, @Param(value = "age") final int age);

    @Query(value = "select m.username from Member m")
    List<String> findUsernameList();

    @Query(value = "select new study.jpa.dto.MemberDto(m) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query(value = "select m from Member m where m.username in :usernames")
    List<Member> findByUsernames(@Param(value = "usernames") final Collection<String> usernames);
//    List<Member> findByUsername(@Param(value = "usernames") final String[] usernames); 배열도 사용 가능

    List<Member> findListByUsername(final String username);//컬렉선

    Member findMemberByUsername(final String username);//단건

    Optional<Member> findOptionalMemberByUsername(final String username);//단건 Optional

    @Query(value = "select m from Member m left join m.team t", countQuery = "select count(m) from Member m")
    Page<Member> findPageByAge(final int age, final Pageable pageable);

    Slice<Member> findSliceByAge(final int age, final Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query(value = "update Member m set m.age = m.age+1 where m.age>=:age")
    int bulkAgePlus(@Param(value = "age") final int age);

    @Query(value = "select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})
    @Query(value = "select m from Member m")
    List<Member> findMemberEntityGraph();

    //    @EntityGraph(attributePaths = {"team"})
    @EntityGraph(value = "Member.all")
    List<Member> findEntityGraphByUsername(@Param(value = "username") final String username);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(final String username);

    @Lock(value = PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(final String username);
}
