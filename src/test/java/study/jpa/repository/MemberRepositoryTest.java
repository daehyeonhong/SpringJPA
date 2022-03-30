package study.jpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.jpa.dto.MemberDto;
import study.jpa.entity.Member;
import study.jpa.entity.Team;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TeamRepository teamRepository;

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
        final List<Member> members = this.memberRepository.findByUsername(Arrays.asList("AAA", "BBB"));
//        final List<Member> members = this.memberRepository.findByUsername(new String[]{"AAA", "BBB"}); 배열도 사용 가능
        //then
        for (final Member member : members) {
            System.out.println("member = " + member);
        }
    }
}
