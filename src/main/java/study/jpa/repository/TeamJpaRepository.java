package study.jpa.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.jpa.entity.Team;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TeamJpaRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    public Team save(final Team team) {
        this.entityManager.persist(team);
        return team;
    }

    public void delete(final Team team) {
        this.entityManager.remove(team);
    }

    public List<Team> findAll() {
        return this.entityManager
                .createQuery("select t from Team t", Team.class)
                .getResultList();
    }

    public Optional<Team> findById(final Long id) {
        return Optional.ofNullable(this.entityManager.find(Team.class, id));
    }

    public long count() {
        return this.entityManager
                .createQuery("select count(t) from Team t", Long.class)
                .getSingleResult();
    }
}
