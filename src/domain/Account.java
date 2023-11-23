package domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Account class used as application user entity
 */
@Getter
@Setter
public class Account extends CommonEntity<Long> {

    private String name;
    private String occupation;

    /**
     * Common constructor used to create new account data, will always set updateDateTime to null.
     * @param id data id
     * @param createDateTime creation timestamp
     * @param name account name
     * @param occupation job description
     */
    public Account(Long id, LocalDateTime createDateTime, String name, String occupation) {
        super(id, createDateTime, null);
        this.name = name;
        this.occupation = occupation;
    }

    /**
     * Used in search
     * @param id Account.id
     */
    public Account(Long id){
        this.id = id;
    }

    @Override
    public String toString() {
        return "id: "+this.getId()+" name: "+this.getName()+ " occupation: "+this.getOccupation() +"\n"
                + "created: "+ this.getCreateDateTime() +" updated: "+this.getUpdateDateTime()+"\n";
    }
}
