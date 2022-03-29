package study.jpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.jpa.entity.Member;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

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
}
