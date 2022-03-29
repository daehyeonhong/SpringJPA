package study.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.jpa.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
