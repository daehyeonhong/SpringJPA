package study.jpa.entity;

import lombok.Getter;
import lombok.experimental.FieldDefaults;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import static lombok.AccessLevel.PRIVATE;

@Getter
@MappedSuperclass
@FieldDefaults(level = PRIVATE)
public class JpaBaseEntity {
    @Column(updatable = false)
    LocalDateTime createdDate;
    LocalDateTime updatedDate;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = now();
        this.createdDate = now;
        this.updatedDate = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedDate = now();
    }
}
