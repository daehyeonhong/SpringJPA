package study.jpa.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.jpa.entity.Member;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    public Member save(final Member member) {
        this.entityManager.persist(member);
        return member;
    }

    public void delete(final Member member) {
        this.entityManager.remove(member);
    }

    public List<Member> findAll() {
        return this.entityManager
                .createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public Optional<Member> findById(final Long id) {
        return Optional.ofNullable(this.find(id));
    }

    public long count() {
        return this.entityManager
                .createQuery("select count(m) from Member m", Long.class)
                .getSingleResult();
    }

    public Member find(final Long id) {
        return this.entityManager.find(Member.class, id);
    }
}
