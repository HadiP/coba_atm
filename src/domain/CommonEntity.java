package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


/**
 * Common class for entity.
 * @param <T>
 */
@Getter
@Setter
@AllArgsConstructor
public abstract class CommonEntity<T> {
    protected T id;
    protected LocalDateTime createDateTime;
    protected LocalDateTime updateDateTime;

    public CommonEntity() {
        createDateTime = LocalDateTime.now();
    }
}
