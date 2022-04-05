package study.jpa.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import study.jpa.entity.Member;
import study.jpa.entity.Team;
import static javax.persistence.criteria.JoinType.INNER;

public class MemberSpecification {
    public static Specification<Member> teamName(final String teamName) {
        if (!StringUtils.hasText(teamName))
            return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.<Member, Team>join("team", INNER).get("name"), teamName);
    }

    public static Specification<Member> userName(final String userName) {
        if (!StringUtils.hasText(userName))
            return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("username"), userName);
    }
}
