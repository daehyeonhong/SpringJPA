package study.jpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.jpa.entity.Member;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {
    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Test
    @DisplayName(value = "멤버 저장 테스트")
    public void testMember() {
        //given
        final Member member = new Member("memberA");
        member.changeUsername("member");
        final Member savedMember = this.memberJpaRepository.save(member);

        //when
        final Member findMember = this.memberJpaRepository.find(savedMember.getId());

        //then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    @DisplayName(value = "기본 CRUD 테스트")
    public void basicCRUD() {
        //given
        final Member member1 = new Member("member1");
        final Member member2 = new Member("member2");
        this.memberJpaRepository.save(member1);
        this.memberJpaRepository.save(member2);
        //when
        final Member findMember1 = this.memberJpaRepository.findById(member1.getId()).orElseThrow();
        final Member findMember2 = this.memberJpaRepository.findById(member2.getId()).orElseThrow();
        //then
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        final List<Member> members = this.memberJpaRepository.findAll();
        assertThat(members.size()).isEqualTo(2);

        final long count = this.memberJpaRepository.count();
        assertThat(count).isEqualTo(2);

        this.memberJpaRepository.delete(member1);
        this.memberJpaRepository.delete(member2);

        final boolean member1IsPresent = this.memberJpaRepository.findById(member1.getId()).isPresent();
        final boolean member2IsPresent = this.memberJpaRepository.findById(member2.getId()).isPresent();

        assertThat(member1IsPresent).isFalse();
        assertThat(member2IsPresent).isFalse();

        final long deletedCount = this.memberJpaRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    @DisplayName(value = "이름과 나이 기준 검색")
    public void findUsernameAndAgeGraterThan() {
        //given
        final Member member1 = new Member("AAA", 10);
        final Member member2 = new Member("AAA", 20);
        final Member member3 = new Member("AAA", 30);
        this.memberJpaRepository.save(member1);
        this.memberJpaRepository.save(member2);
        this.memberJpaRepository.save(member3);
        //when
        final List<Member> memberList = this.memberJpaRepository
                .findByUsernameAndAgeGreaterThan("AAA", 10);
        //then
        assertThat(memberList.size()).isEqualTo(2);
    }
}
