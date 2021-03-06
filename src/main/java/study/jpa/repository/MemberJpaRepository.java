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

    public List<Member> findByUsernameAndAgeGreaterThan(final String username, final int age) {
        return this.entityManager
                .createQuery("select m from Member m where m.username=:username and m.age>:age", Member.class)
                .setParameter("username", username)
                .setParameter("age", age)
                .getResultList();
    }

    public List<Member> findByUsername(final String username) {
        return this.entityManager.createNamedQuery("Member.findByUsername", Member.class)
                .setParameter("username", username)
                .getResultList();
    }


    public List<Member> findByPage(final int age, final int offset, final int limit) {
        return this.entityManager.createQuery("select m from Member m where m.age=:age order by m.username desc", Member.class)
                .setParameter("age", age)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public long totalCount(final int age) {
        return this.entityManager.createQuery("select count(m) from Member m where m.age=:age", Long.class)
                .setParameter("age", age)
                .getSingleResult();
    }

    public int bulkAgePlus(final int age) {
        return this.entityManager.createQuery("update Member m set m.age=m.age+1 where m.age>=:age")
                .setParameter("age", age)
                .executeUpdate();
    }
}
