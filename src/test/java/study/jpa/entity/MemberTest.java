package study.jpa.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DisplayName(value = "엔티티 테스트")
    public void testEntity() {
        //given
        final Team teamA = new Team("teamA");
        final Team teamB = new Team("teamB");
        this.entityManager.persist(teamA);
        this.entityManager.persist(teamB);
        //when
        final Member member1 = new Member("member1", 10, teamA);
        final Member member2 = new Member("member2", 20, teamA);
        final Member member3 = new Member("member3", 30, teamB);
        final Member member4 = new Member("member4", 40, teamB);

        this.entityManager.persist(member1);
        this.entityManager.persist(member2);
        this.entityManager.persist(member3);
        this.entityManager.persist(member4);

        // 초기화
        this.entityManager.flush();
        this.entityManager.clear();
        //then
        final List<Member> members = this.entityManager.createQuery("select m from Member m", Member.class).getResultList();

        for (final Member member : members) {
            System.out.println("member = " + member);
            System.out.println("-> team = " + member.getTeam());
        }
    }
}
