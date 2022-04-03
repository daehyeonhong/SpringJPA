package study.jpa.repository;

import lombok.RequiredArgsConstructor;
import study.jpa.entity.Member;
import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
    private final EntityManager entityManager;

    @Override
    public List<Member> findMemberCustom() {
        return this.entityManager.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
