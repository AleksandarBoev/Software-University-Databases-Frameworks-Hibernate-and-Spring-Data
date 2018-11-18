package p04_hospital_database.root_entities;

import javax.persistence.Column;
import javax.persistence.Entity;

//@Entity
public abstract class BaseName extends BaseId {
    private String name;

    protected BaseName() {
    }

    protected BaseName(String name) {
        this.setName(name);
    }

    @Column(length = 100)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (name == null || "".equals(name) || name.length() > 100)
            throw new IllegalArgumentException("Exception in entity `" + this.getClass().getSimpleName() + "` - Invalid name!");

        this.name = name;
    }

    @Override
    public String toString() {
        return "{" + this.getClass().getSimpleName() +
                "name='" + name + '\'' + ", ";
    }
}
