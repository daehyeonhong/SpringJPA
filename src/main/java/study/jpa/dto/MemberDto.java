package study.jpa.dto;

import lombok.Getter;
import lombok.experimental.FieldDefaults;
import study.jpa.entity.Member;
import java.io.Serializable;
import static lombok.AccessLevel.PRIVATE;

@Getter
@FieldDefaults(level = PRIVATE)
public class MemberDto implements Serializable {
    final String username;
    final int age;
    final String teamName;

    public MemberDto(final Member member) {
        this.username = member.getUsername();
        this.age = member.getAge();
        this.teamName = member.getTeam().getName();
    }

    @Override
    public String toString() {
        return "MemberDto{" +
                "username='" + username + '\'' +
                ", age=" + age +
                ", teamName='" + teamName + '\'' +
                '}';
    }
}
