package study.jpa.repository;

import study.jpa.entity.Member;
import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
}
