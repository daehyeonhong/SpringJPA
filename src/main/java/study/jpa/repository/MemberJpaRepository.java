package study.jpa.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.jpa.entity.Member;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepository {
    @PersistenceContext
    private final EntityManager entityManager;


    public Member save(final Member member) {
        this.entityManager.persist(member);
        return member;
    }

    public Member find(final Long id) {
        return this.entityManager.find(Member.class, id);
    }
}
