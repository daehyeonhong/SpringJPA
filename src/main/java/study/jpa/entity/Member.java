package study.jpa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@FieldDefaults(level = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
@NamedQuery(
        name = "Member.findByUsername",
        query = "select m from Member m where m.username=:username"
)
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_di")
    Long id;
    String username;
    int age;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "team_id")
    Team team;

    public Member(final String username) {
        this.username = username;
    }

    public Member(final String username, final int age) {
        this.username = username;
        this.age = age;
    }

    public Member(final String username, final int age, final Team team) {
        this.username = username;
        this.age = age;
        if (team != null) this.changeTeam(team);
    }

    public void changeUsername(final String username) {
        this.username = username;
    }

    public void changeTeam(final Team team) {
        this.team = team;
        team.getMembers().add(this);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "username = " + username + ", " +
                "age = " + age + ")";
    }
}
