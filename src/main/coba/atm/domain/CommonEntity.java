package coba.atm.domain;

import java.time.LocalDateTime;


/**
 * Common class for entity.
 * @param <T> id of entity
 */
public abstract class CommonEntity<T> {
    protected T id;
    protected LocalDateTime createDateTime;
    protected LocalDateTime updateDateTime;

    protected CommonEntity(T id, LocalDateTime createDateTime, LocalDateTime updateDateTime) {
        this.id = id;
        this.createDateTime = createDateTime;
        this.updateDateTime = updateDateTime;
    }

    protected CommonEntity() {
        createDateTime = LocalDateTime.now();
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }
}
