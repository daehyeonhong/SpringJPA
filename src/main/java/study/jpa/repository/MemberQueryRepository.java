package study.jpa.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.jpa.entity.Member;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {
    private final EntityManager entityManager;

    List<Member> findAllMembers() {
        return this.entityManager.createQuery("select m from Member m", Member.class).getResultList();
    }
}
