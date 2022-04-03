package study.jpa.entity;

import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import static lombok.AccessLevel.PRIVATE;

@Getter
@MappedSuperclass
@FieldDefaults(level = PRIVATE)
public class BaseTimeEntity {
    @CreatedDate
    @Column(updatable = false)
    LocalDateTime createdDate;
    @LastModifiedDate
    LocalDateTime lastModifiedDate;
}
