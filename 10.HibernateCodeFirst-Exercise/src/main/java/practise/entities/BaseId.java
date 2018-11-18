package practise.entities;

import javax.persistence.*;

//@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseId {
    private long id;

    protected BaseId() {
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.TABLE)
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
