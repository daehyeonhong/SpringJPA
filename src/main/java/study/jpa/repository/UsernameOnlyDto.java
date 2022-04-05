package study.jpa.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
public class UsernameOnlyDto {
    final String username;
}
