package p04_hospital_database.root_entities;

import javax.persistence.*;

//@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseId {
    private int id;

    protected BaseId() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
