package practise.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

//@Entity
@Table
public class Department extends BaseId {
    private String name;

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    @Column
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
