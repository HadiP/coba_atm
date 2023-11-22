package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Account class used as application user entity
 */
@Getter
@Setter
@AllArgsConstructor
public class Account extends CommonEntity<Long> {

    private String name;
    private String occupation;

    /**
     * Common constructor used to create new account data, will always set updateDateTime to null.
     * @param id
     * @param createDateTime
     * @param name
     * @param occupation
     */
    public Account(Long id, LocalDateTime createDateTime, String name, String occupation) {
        super(id, createDateTime, null);
        this.name = name;
        this.occupation = occupation;
    }
}
