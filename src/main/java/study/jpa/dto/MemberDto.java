package study.jpa.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import java.io.Serializable;
import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
public class MemberDto implements Serializable {
    final String username;
    final int age;
    final String teamName;

    @Override
    public String toString() {
        return "MemberDto{" +
                "username='" + username + '\'' +
                ", age=" + age +
                ", teamName='" + teamName + '\'' +
                '}';
    }
}
