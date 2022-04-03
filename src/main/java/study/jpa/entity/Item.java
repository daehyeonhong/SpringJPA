package study.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Persistable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@FieldDefaults(level = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Item extends BaseTimeEntity implements Serializable, Persistable<String> {
    @Id
    String id;

    @Override
    public boolean isNew() {
        return super.getCreatedDate() == null;
    }
}
