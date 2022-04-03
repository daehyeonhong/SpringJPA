package study.jpa.entity;

import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import static lombok.AccessLevel.PRIVATE;

@Getter
@MappedSuperclass
@FieldDefaults(level = PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {
    @CreatedDate
    @Column(updatable = false)
    LocalDateTime createdDate;
    @LastModifiedDate
    LocalDateTime lastModifiedDate;
}
