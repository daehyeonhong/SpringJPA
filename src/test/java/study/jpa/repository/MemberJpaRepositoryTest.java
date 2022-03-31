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

    @Test
    @DisplayName(value = "네임드쿼리 테스트")
    public void findByUsernameNamedQuery() {
        //given
        final Member member1 = new Member("AAA", 10);
        final Member member2 = new Member("BBB", 20);
        this.memberJpaRepository.save(member1);
        this.memberJpaRepository.save(member2);
        //when
        final List<Member> result = this.memberJpaRepository.findByUsername("AAA");
        //then
        assertThat(result.get(0)).isEqualTo(member1);
    }

    @Test
    @DisplayName(value = "JPA Paging Test")
    public void findByPageTest() {
        //given
        final int age = 10;
        final int offset = 0;
        final int limit = 3;
        final Member member1 = new Member("member1", age);
        final Member member2 = new Member("member2", age);
        final Member member3 = new Member("member3", age);
        final Member member4 = new Member("member4", age);
        final Member member5 = new Member("member5", age);
        this.memberJpaRepository.save(member1);
        this.memberJpaRepository.save(member2);
        this.memberJpaRepository.save(member3);
        this.memberJpaRepository.save(member4);
        this.memberJpaRepository.save(member5);
        //when
        final List<Member> result = this.memberJpaRepository.findByPage(age, offset, limit);
        long totalCount = this.memberJpaRepository.totalCount(age);
        //then
        assertThat(result.size()).isEqualTo(limit);
        assertThat(totalCount).isEqualTo(5);
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
        this.memberJpaRepository.save(member1);
        this.memberJpaRepository.save(member2);
        this.memberJpaRepository.save(member3);
        this.memberJpaRepository.save(member4);
        this.memberJpaRepository.save(member5);
        //when
        final int resultCount = this.memberJpaRepository.bulkAgePlus(20);
        //then
        assertThat(resultCount).isEqualTo(3);
    }
}
