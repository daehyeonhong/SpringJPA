package study.jpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.jpa.dto.MemberDto;
import study.jpa.entity.Member;
import study.jpa.entity.Team;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MemberQueryRepository memberQueryRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DisplayName(value = "멤버 저장 테스트")
    public void testMember() {
        //given
        final Member member = new Member("memberA");
        member.changeUsername("member");
        final Member savedMember = this.memberRepository.save(member);

        //when
        final Member findMember = this.memberRepository.findById(savedMember.getId()).orElseThrow();

        //then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }


    @Test
    @DisplayName(value = "SpringJpa CRUD 테스트")
    public void basicCRUD() {
        //given
        final Member member1 = new Member("member1");
        final Member member2 = new Member("member2");
        this.memberRepository.save(member1);
        this.memberRepository.save(member2);
        //when
        final Member findMember1 = this.memberRepository.findById(member1.getId()).orElseThrow();
        final Member findMember2 = this.memberRepository.findById(member2.getId()).orElseThrow();
        //then
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        final List<Member> members = this.memberRepository.findAll();
        assertThat(members.size()).isEqualTo(2);

        final long count = this.memberRepository.count();
        assertThat(count).isEqualTo(2);

        this.memberRepository.delete(member1);
        this.memberRepository.delete(member2);

        final boolean member1IsPresent = this.memberRepository.findById(member1.getId()).isPresent();
        final boolean member2IsPresent = this.memberRepository.findById(member2.getId()).isPresent();

        assertThat(member1IsPresent).isFalse();
        assertThat(member2IsPresent).isFalse();

        final long deletedCount = this.memberRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    @DisplayName(value = "이름과 나이 기준 검색")
    public void findUsernameAndAgeGraterThan() {
        //given
        final Member member1 = new Member("AAA", 10);
        final Member member2 = new Member("AAA", 20);
        final Member member3 = new Member("AAA", 30);
        this.memberRepository.save(member1);
        this.memberRepository.save(member2);
        this.memberRepository.save(member3);
        //when
        final List<Member> memberList = this.memberRepository
                .findByUsernameAndAgeGreaterThan("AAA", 10);
        //then
        assertThat(memberList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName(value = "네임드쿼리 테스트")
    public void findByUsernameNamedQuery() {
        //given
        final Member member1 = new Member("AAA", 10);
        final Member member2 = new Member("BBB", 20);
        this.memberRepository.save(member1);
        this.memberRepository.save(member2);
        //when
        final List<Member> result = this.memberRepository.findByUsername("AAA");
        //then
        assertThat(result.get(0)).isEqualTo(member1);
    }

    @Test
    @DisplayName(value = "레포지토리 네임드쿼리 테스트")
    public void findUserTestQuery() {
        //given
        final Member member1 = new Member("AAA", 10);
        final Member member2 = new Member("BBB", 20);
        this.memberRepository.save(member1);
        this.memberRepository.save(member2);
        //when
        final List<Member> result = this.memberRepository.findMember("AAA", 10);
        //then
        assertThat(result.get(0)).isEqualTo(member1);
    }

    @Test
    @DisplayName(value = "레포지토리 네임드쿼리 이름목록 테스트")
    public void findUsernameListTestQuery() {
        //given
        final Member member1 = new Member("AAA", 10);
        final Member member2 = new Member("BBB", 20);
        this.memberRepository.save(member1);
        this.memberRepository.save(member2);
        //when
        final List<String> usernameList = this.memberRepository.findUsernameList();
        //then
        for (String username : usernameList) {
            System.out.println("username = " + username);
        }
    }

    @Test
    @DisplayName(value = "레포지토리 네임드쿼리 DTO 테스트")
    public void findMemberToDto() {
        //given
        final Team teamA = new Team("teamA");
        this.teamRepository.save(teamA);
        final Member member1 = new Member("AAA", 10);
        final Member member2 = new Member("BBB", 20);
        member1.changeTeam(teamA);
        member2.changeTeam(teamA);
        this.memberRepository.save(member1);
        this.memberRepository.save(member2);
        //when
        final List<MemberDto> memberDtos = this.memberRepository.findMemberDto();
        //then
        for (MemberDto memberDto : memberDtos) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    @Test
    @DisplayName(value = "레포지토리 네임드쿼리 컬렉션 테스트")
    public void findByUsername() {
        //given
        final Member member1 = new Member("AAA", 10);
        final Member member2 = new Member("BBB", 20);
        this.memberRepository.save(member1);
        this.memberRepository.save(member2);
        //when
        final List<Member> members = this.memberRepository.findByUsernames(Arrays.asList("AAA", "BBB"));
//        final List<Member> members = this.memberRepository.findByUsername(new String[]{"AAA", "BBB"}); 배열도 사용 가능
        //then
        for (final Member member : members) {
            System.out.println("member = " + member);
        }
    }

    @Test
    @DisplayName(value = "리턴타입 테스트")
    public void returnType() {
        //given
        final Member member1 = new Member("AAA", 10);
        final Member member2 = new Member("BBB", 20);
        this.memberRepository.save(member1);
        this.memberRepository.save(member2);
        //when
        final List<Member> members = this.memberRepository.findListByUsername("AAA");
        final Member member = this.memberRepository.findMemberByUsername("AAA");
        final Optional<Member> optionalMember = this.memberRepository.findOptionalMemberByUsername("AAA");
        final List<Member> result = this.memberRepository.findListByUsername("asdasf");
        final Member returnNull = this.memberRepository.findMemberByUsername("asdasf");
        final Optional<Member> optionalNull = this.memberRepository.findOptionalMemberByUsername("asdasf");
        //then
        for (final Member mem : members) System.out.println("mem = " + mem);
        System.out.println("member = " + member);
        System.out.println("optionalMember = " + optionalMember.orElseThrow());
        for (final Member item : result) System.out.println("item = " + item);
        System.out.println("returnNull = " + returnNull);
        System.out.println("optionalNull = " + optionalNull);
    }

    @Test
    @DisplayName(value = "JPA Paging Test")
    public void findByPageTest() {
        //given
        final int age = 10;
        final int size = 3;
        final Member member1 = new Member("member1", age);
        final Member member2 = new Member("member2", age);
        final Member member3 = new Member("member3", age);
        final Member member4 = new Member("member4", age);
        final Member member5 = new Member("member5", age);
        this.memberRepository.save(member1);
        this.memberRepository.save(member2);
        this.memberRepository.save(member3);
        this.memberRepository.save(member4);
        this.memberRepository.save(member5);
        //when
        final PageRequest pageRequest = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "username"));
        final Page<Member> pageMemberByAge = this.memberRepository.findPageByAge(age, pageRequest);
        //then

        final Page<MemberDto> memberDtoPage = pageMemberByAge.map(MemberDto::new);
        final List<MemberDto> members = memberDtoPage.getContent();
        assertThat(members.size()).isEqualTo(size);
        assertThat(memberDtoPage.getTotalElements()).isEqualTo(5);
        assertThat(memberDtoPage.getNumber()).isEqualTo(0);
        assertThat(memberDtoPage.getTotalPages()).isEqualTo(2);
        assertThat(memberDtoPage.isFirst()).isTrue();
        assertThat(memberDtoPage.hasNext()).isTrue();
    }

    @Test
    @DisplayName(value = "JPA Slice Test")
    public void findBySliceTest() {
        //given
        final int age = 10;
        final int size = 3;
        final Member member1 = new Member("member1", age);
        final Member member2 = new Member("member2", age);
        final Member member3 = new Member("member3", age);
        final Member member4 = new Member("member4", age);
        final Member member5 = new Member("member5", age);
        this.memberRepository.save(member1);
        this.memberRepository.save(member2);
        this.memberRepository.save(member3);
        this.memberRepository.save(member4);
        this.memberRepository.save(member5);
        //when
        final PageRequest pageRequest = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "username"));
        final Slice<Member> pageMemberByAge = this.memberRepository.findSliceByAge(age, pageRequest);
        //then
        final List<Member> members = pageMemberByAge.getContent();
        assertThat(members.size()).isEqualTo(size);
        assertThat(pageMemberByAge.getNumber()).isEqualTo(0);
        assertThat(pageMemberByAge.isFirst()).isTrue();
        assertThat(pageMemberByAge.hasNext()).isTrue();
    }

    @Test
    @DisplayName(value = "벌크성 업데이트 쿼리")
    public void bulkUpdate() {
        //given
        final Member member1 = new Member("member1", 10);
        final Member member2 = new Member("member2", 19);
        final Member member3 = new Member("member3", 20);
        final Member member4 = new Member("member4", 21);
        final Member member5 = new Member("member5", 40);
        this.memberRepository.save(member1);
        this.memberRepository.save(member2);
        this.memberRepository.save(member3);
        this.memberRepository.save(member4);
        this.memberRepository.save(member5);
        //when
        final int resultCount = this.memberRepository.bulkAgePlus(20);
//        this.entityManager.clear(); //@Modifying(clearAutomatically = true) 로 대체

        final Member result = this.memberRepository.findByUsername("member5").get(0);
        System.out.println("result = " + result);
        System.out.println("member5 = " + member5);
        //then
        assertThat(resultCount).isEqualTo(3);
    }

    @Test
    @DisplayName(value = "지연로딩")
    public void findMemberLazy() {
        //given
        //member1->teamA
        //member2->teamB
        final Team teamA = new Team("teamA");
        final Team teamB = new Team("teamB");
        this.teamRepository.save(teamA);
        this.teamRepository.save(teamB);

        final Member member1 = new Member("member1", 10, teamA);
        final Member member2 = new Member("member2", 10, teamB);
        this.memberRepository.save(member1);
        this.memberRepository.save(member2);
        this.entityManager.flush();
        this.entityManager.clear();

        //when
        final List<Member> memberList = this.memberRepository.findAll();
        this.entityManager.flush();
        this.entityManager.clear();
        memberList.forEach(member -> {
            System.out.println("member = " + member.getUsername());
            System.out.println("teamClass = " + member.getTeam().getClass());
            System.out.println("team = " + member.getTeam().getName());
        });
        final List<Member> memberFetchJoinList = this.memberRepository.findMemberFetchJoin();
        this.entityManager.flush();
        this.entityManager.clear();
        memberFetchJoinList.forEach(member -> {
            System.out.println("member = " + member.getUsername());
            System.out.println("teamClass = " + member.getTeam().getClass());
            System.out.println("team = " + member.getTeam().getName());
        });
        final List<Member> memberEntityGraph = this.memberRepository.findMemberEntityGraph();
        memberEntityGraph.forEach(member -> {
            System.out.println("member = " + member.getUsername());
            System.out.println("teamClass = " + member.getTeam().getClass());
            System.out.println("team = " + member.getTeam().getName());
        });
        this.entityManager.flush();
        this.entityManager.clear();
        final List<Member> entityGraphByUsername = this.memberRepository.findEntityGraphByUsername("member1");
        entityGraphByUsername.forEach(member -> {
            System.out.println("member = " + member.getUsername());
            System.out.println("teamClass = " + member.getTeam().getClass());
            System.out.println("team = " + member.getTeam().getName());
        });
    }

    @Test
    @DisplayName(value = "쿼리 힌트 테스트")
    public void queryHint() {
        //given
        final Member member = new Member("member1", 10);
        this.memberRepository.save(member);
        this.entityManager.flush();
        this.entityManager.clear();
        //when
        final Member findMember = this.memberRepository.findReadOnlyByUsername(member.getUsername());
        findMember.changeUsername("member2");
        //then
        this.entityManager.flush();
        final List<Member> lockByUsername = this.memberRepository.findLockByUsername(member.getUsername());
        System.out.println("lockByUsername = " + lockByUsername);
    }

    @Test
    @DisplayName(value = "커스텀 메소드 호출")
    public void callCustomTest() {
        //given
        final Member member = new Member("member1", 10);
        this.memberRepository.save(member);
        this.entityManager.flush();
        this.entityManager.clear();
        //when
        final List<Member> memberCustom = this.memberRepository.findMemberCustom();
        final List<Member> allMembers = this.memberQueryRepository.findAllMembers();
        //then
        assertThat(memberCustom).isEqualTo(allMembers);
    }
}
